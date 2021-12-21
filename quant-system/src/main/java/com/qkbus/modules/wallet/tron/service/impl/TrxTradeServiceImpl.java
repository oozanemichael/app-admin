
package com.qkbus.modules.wallet.tron.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.constant.RedisConstant;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.enums.CurrencyEnum;
import com.qkbus.enums.WalletLogEnum;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.assets.domain.MemberWallet;
import com.qkbus.modules.assets.service.MemberWalletService;
import com.qkbus.modules.assets.service.dto.MemberWalletLogParam;
import com.qkbus.modules.wallet.tron.domain.TrxTrade;
import com.qkbus.modules.wallet.tron.service.TrxTradeService;
import com.qkbus.modules.wallet.tron.service.dto.TrxTradeDto;
import com.qkbus.modules.wallet.tron.service.dto.TrxTradeQueryCriteria;
import com.qkbus.modules.wallet.tron.service.mapper.TrxTradeMapper;
import com.qkbus.utils.FileUtil;
import com.qkbus.utils.RedisUtils;
import com.qkbus.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
@Service
@Slf4j
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TrxTradeServiceImpl extends BaseServiceImpl<TrxTradeMapper, TrxTrade> implements TrxTradeService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;
    private final MemberWalletService memberWalletService;

    @Override
    public Map<String, Object> queryAll(TrxTradeQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<TrxTrade> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), TrxTradeDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<TrxTrade> queryAll(TrxTradeQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(TrxTrade.class, criteria));
    }


    @Override
    public void download(List<TrxTradeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TrxTradeDto TrxTrade : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", TrxTrade.getUserId());
            map.put("区块ID", TrxTrade.getBlockId());
            map.put("区块号", TrxTrade.getBlockNumber());
            map.put("签名", TrxTrade.getSignature());
            map.put("交易ID", TrxTrade.getTxId());
            map.put("转入地址", TrxTrade.getFromAddr());
            map.put("数量", TrxTrade.getAmount());
            map.put("hex数量", TrxTrade.getHexAmount());
            map.put("接受地址", TrxTrade.getToAddr());
            map.put("状态", TrxTrade.getStatus());
            map.put("归集ID", TrxTrade.getCollectId());
            map.put("描述", TrxTrade.getCommont());
            map.put("币种", TrxTrade.getCurrency());
            map.put("合约地址", TrxTrade.getContractAddress());
            map.put("ABI合约", TrxTrade.getAbi());
            map.put("审核人", TrxTrade.getAdminName());
            map.put("充值时间", TrxTrade.getRechargeAt());
            map.put("创建时间", TrxTrade.getGmtCreate());
            map.put("修改时间", TrxTrade.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(TrxTrade resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TrxTrade resources) {
        TrxTrade trxTrade = this.getById(resources.getId());
        if (ObjectUtil.isNull(trxTrade)) {
            throw new BadRequestException("充值记录不存在");
        }
        if (!redisUtils.setIfAbsent(RedisConstant.CHECK_TRADE_LOCK + trxTrade.getId(), RedisConstant.LOCK_TIME_OUT)) {
            throw new BadRequestException("操作频繁，请稍后再试!");
        }
        TrxTrade updateTrxTrade = new TrxTrade();
        updateTrxTrade.setId(resources.getId());
        //只有状态等于为审核时可以
        if (trxTrade.getStatus() == TrxTrade.TRX_TYPE_ARRIVAL) {
            //入账
            if (resources.getStatus() == TrxTrade.TRX_TYPE_ENTERED) {
                updateTrxTrade.setStatus(resources.getStatus());
                MemberWallet myWallet = memberWalletService.getMyWallet(trxTrade.getUserId(), trxTrade.getCurrency());
                if (Objects.isNull(myWallet)) {
                    throw new BadRequestException("该账户异常:" + trxTrade.getUserId());
                }
                MemberWalletLogParam memberWalletLog = new MemberWalletLogParam();
                memberWalletLog.setUid(trxTrade.getUserId());
                memberWalletLog.setAmount(trxTrade.getAmount());
                memberWalletLog.setCurrency(trxTrade.getCurrency());
                memberWalletService.updateWallet(memberWalletLog);
                //如果可用减去提币资产小于  负数表示需要发放欠款收益
                if (trxTrade.getCurrency().equals("USDT")) {
                    BigDecimal sendValue = trxTrade.getAmount();
                    BigDecimal balance = myWallet.getBalance().subtract(myWallet.getFrozenBalance());
                    if (NumberUtil.isLess(balance, BigDecimal.ZERO)) {
                        if (NumberUtil.isGreaterOrEqual(trxTrade.getAmount(), balance.negate())) {
                            sendValue = balance.negate();
                        }
                        // rpush redis中等待收益发放
                        log.info("欠费用户{}，充值金额{}，等待收益发放", trxTrade.getUserId(), sendValue);
                        TrxTrade revenueDTO = new TrxTrade();
                        revenueDTO.setAmount(sendValue);
                        revenueDTO.setCurrency(trxTrade.getCurrency());
                        revenueDTO.setUserId(trxTrade.getUserId());
                        redisUtils.rPush(RedisConstant.WITHDRAW_ROBOT_REVENUE, JSONUtil.toJsonStr(revenueDTO));
                    }
                }
                //拒绝
            }
            updateTrxTrade.setAdminName(SecurityUtils.getUsername());
            UpdateWrapper<TrxTrade> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id", trxTrade.getId());
            updateWrapper.eq("status", trxTrade.getStatus());
            this.update(updateTrxTrade, updateWrapper);
        } else {
            throw new BadRequestException("此笔订单已审核");
        }
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
