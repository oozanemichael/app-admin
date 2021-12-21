
package com.qkbus.modules.withdraw.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.modules.recharge.domain.Recharge;
import com.qkbus.modules.vo.StatisticalVo;
import com.qkbus.modules.withdraw.domain.Withdraw;
import com.qkbus.modules.withdraw.service.WithdrawService;
import com.qkbus.modules.withdraw.service.dto.WithdrawDto;
import com.qkbus.modules.withdraw.service.dto.WithdrawQueryCriteria;
import com.qkbus.modules.withdraw.service.dto.WithdrawRobotRevenue;
import com.qkbus.modules.withdraw.service.mapper.WithdrawMapper;
import com.qkbus.utils.FileUtil;
import com.qkbus.utils.RedisUtils;
import com.qkbus.utils.SecurityUtils;
import lombok.AllArgsConstructor;
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
 * @date 2021-05-12
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WithdrawServiceImpl extends BaseServiceImpl<WithdrawMapper, Withdraw> implements WithdrawService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    private final MemberService memberService;

    private final MemberWalletService memberWalletService;

    @Override

    public Map<String, Object> queryAll(WithdrawQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Withdraw> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), WithdrawDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<Withdraw> queryAll(WithdrawQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Withdraw.class, criteria));
    }


    @Override
    public void download(List<WithdrawDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WithdrawDto Withdraw : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", Withdraw.getUid());
            map.put("公链", Withdraw.getChainType());
            map.put("币种", Withdraw.getCurrency());
            map.put("申请数量", Withdraw.getAmount());
            map.put("手续费", Withdraw.getFee());
            map.put("手续费币种", Withdraw.getFeeCurrency());
            map.put("实际提现数量", Withdraw.getRealAmount());
            map.put("提币地址", Withdraw.getWithdrawAddress());
            map.put("操作人", Withdraw.getAdminName());
            map.put("状态", Withdraw.getStatus());
            map.put("处理说明", Withdraw.getRemark());
            map.put("转账HASH", Withdraw.getMotionHash());
            map.put("创建时间", Withdraw.getGmtCreate());
            map.put("修改时间", Withdraw.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Withdraw resources) {
        audit(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void audit(Withdraw bo) {
        Withdraw withdraw = this.getById(bo.getId());
        if (ObjectUtil.isNull(withdraw)) {
            throw new BadRequestException("提币记录不存在");
        }
        if (!redisUtils.setIfAbsent(RedisConstant.CHECK_WITHDRAW_LOCK + withdraw.getId(), RedisConstant.LOCK_TIME_OUT)) {
            throw new BadRequestException("操作频繁，请稍后再试!");
        }
        Withdraw updateWithdraw = new Withdraw();
        updateWithdraw.setId(bo.getId());
        //当提币拒绝时需要输入拒绝的原因同时退还金额
        if (withdraw.getStatus() == Withdraw.AUDIT) {
            updateWithdraw.setStatus(bo.getStatus());
            updateWithdraw.setRemark(bo.getRemark());
            //拒绝  只需要 归还冻结资产
            if (bo.getStatus() == Withdraw.EXTRACT_REFUSE) {
                MemberWallet myWallet = memberWalletService.getMyWallet(withdraw.getUid(), withdraw.getCurrency());
                if (Objects.isNull(myWallet)) {
                    throw new BadRequestException("该账户异常:" + withdraw.getUid());
                }
                MemberWalletLogParam memberWalletLog = new MemberWalletLogParam();
                memberWalletLog.setFrozenBalance(withdraw.getAmount().negate());
                memberWalletLog.setUid(withdraw.getUid());
                memberWalletLog.setCurrency(withdraw.getCurrency());
                memberWalletLog.setLogType(WalletLogEnum.LOG_TYPE_WITHDRAW.getLogType());
                memberWalletLog.setComment(WalletLogEnum.LOG_TYPE_WITHDRAW.getComment());
                memberWalletService.updateWallet(memberWalletLog);
                //入账
            } else if (bo.getStatus() == Withdraw.EXTRACT_PASS) {
                //修改可用
                MemberWalletLogParam memberWalletLog = new MemberWalletLogParam();
                memberWalletLog.setAmount(withdraw.getAmount().negate());
                memberWalletLog.setFrozenBalance(withdraw.getAmount().negate());
                memberWalletLog.setUid(withdraw.getUid());
                memberWalletLog.setCurrency(withdraw.getCurrency());
                memberWalletLog.setLogType(WalletLogEnum.LOG_TYPE_WITHDRAW.getLogType());
                memberWalletLog.setComment(WalletLogEnum.LOG_TYPE_WITHDRAW.getComment());
                memberWalletService.updateWallet(memberWalletLog);
            } else {
                throw new BadRequestException("网络异常");
            }
            updateWithdraw.setAdminName(SecurityUtils.getUsername());
            UpdateWrapper<Withdraw> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id", withdraw.getId());
            updateWrapper.eq("status", withdraw.getStatus());
            this.update(updateWithdraw, updateWrapper);
        } else {
            throw new BadRequestException("此笔订单已审核");
        }
    }

    @Override
    public Map<String, Object> statisticalWithdraw() {
        //获取总提币量
        //获取总提币量
        QueryWrapper<Withdraw> queryWrapper = new QueryWrapper<Withdraw>();
        queryWrapper.select("ifnull(sum(real_amount),0) as realAmount");
        queryWrapper.eq("currency", CurrencyEnum.USDT.getCurrency());
        Withdraw one = this.getOne(queryWrapper);
        Date beginTime = DateUtil.beginOfDay(DateUtil.date());
        Date endTime = DateUtil.endOfDay(DateUtil.date());
        queryWrapper.between("gmt_create", beginTime, endTime);

        Withdraw day = this.getOne(queryWrapper);

        //获取最近七天的提笔量
        List<StatisticalVo> statisticalVos = baseMapper.statisticalWeek();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", one.getRealAmount());
        map.put("day", day.getRealAmount());
        map.put("record", statisticalVos);
        return map;
    }
}
