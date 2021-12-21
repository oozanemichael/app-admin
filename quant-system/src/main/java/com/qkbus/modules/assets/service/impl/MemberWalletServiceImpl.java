
package com.qkbus.modules.assets.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.constant.RedisConstant;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.enums.WalletLogEnum;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.assets.domain.MemberWallet;
import com.qkbus.modules.assets.service.MemberWalletLogService;
import com.qkbus.modules.assets.service.MemberWalletService;
import com.qkbus.modules.assets.service.dto.MemberWalletDto;
import com.qkbus.modules.assets.service.dto.MemberWalletLogParam;
import com.qkbus.modules.assets.service.dto.MemberWalletQueryCriteria;
import com.qkbus.modules.assets.service.mapper.MemberWalletMapper;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.utils.FileUtil;
import com.qkbus.utils.RedisUtils;
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
 * @date 2021-05-11
 */
@Service
@AllArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MemberWalletServiceImpl extends BaseServiceImpl<MemberWalletMapper, MemberWallet> implements MemberWalletService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    private final MemberService memberService;
    private final MemberWalletLogService memberWalletLogService;

    @Override

    public Map<String, Object> queryAll(MemberWalletQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<MemberWallet> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MemberWalletDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<MemberWallet> queryAll(MemberWalletQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(MemberWallet.class, criteria));
    }


    @Override
    public void download(List<MemberWalletDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberWalletDto MemberWallet : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", MemberWallet.getUid());
            map.put("币种", MemberWallet.getCurrency());
            map.put("余额", MemberWallet.getBalance());
            map.put("冻结资产", MemberWallet.getFrozenBalance());
            map.put("乐观锁", MemberWallet.getVersion());
            map.put("创建时间", MemberWallet.getGmtCreate());
            map.put("修改时间", MemberWallet.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void create(MemberWallet resources) {
        //获取用户信息
        Member member = memberService.getMemberByUid(resources.getUid());
        if (ObjectUtil.isEmpty(member)) {
            throw new BadRequestException("用户UID:{},不存在!" + resources.getUid());
        }
        MemberWallet myWallet = this.getMyWallet(resources.getUid(), resources.getCurrency());
        if (ObjectUtil.isNotNull(myWallet)) {
            throw new BadRequestException("用户UID:" + resources.getUid() + ",存在:" + resources.getCurrency() + "资产!");
        }
        //修改可用
        MemberWalletLogParam memberWalletLog = new MemberWalletLogParam();
        memberWalletLog.setAmount(resources.getBalance());
        memberWalletLog.setFrozenBalance(resources.getFrozenBalance());
        memberWalletLog.setUid(resources.getUid());
        memberWalletLog.setCurrency(resources.getCurrency());
        memberWalletLog.setLogType(WalletLogEnum.HAND_UPDATE.getLogType());
        memberWalletLog.setComment(WalletLogEnum.HAND_UPDATE.getComment());
        updateWallet(memberWalletLog);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void update(MemberWallet resources) {
        MemberWallet myWallet = this.getById(resources.getId());
        if (ObjectUtil.isNotNull(myWallet)) {
            BigDecimal balance = resources.getBalance().subtract(myWallet.getBalance());
            BigDecimal frozenBalance = resources.getFrozenBalance().subtract(myWallet.getFrozenBalance());
            //修改资产
            MemberWalletLogParam memberWalletLog = new MemberWalletLogParam();
            memberWalletLog.setAmount(balance);
            memberWalletLog.setFrozenBalance(frozenBalance);
            memberWalletLog.setUid(myWallet.getUid());
            memberWalletLog.setCurrency(myWallet.getCurrency());
            memberWalletLog.setLogType(WalletLogEnum.HAND_UPDATE.getLogType());
            memberWalletLog.setComment(WalletLogEnum.HAND_UPDATE.getComment());
            updateWallet(memberWalletLog);
        }
    }

    @Override

    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWallet(MemberWalletLogParam memberWalletLog) {
        if (!redisUtils.setIfAbsent(RedisConstant.ACCOUNT_LOCK + memberWalletLog.getUid(), 3)) {
            throw new BadRequestException("操作频繁，请稍后再试!");
        }
        try {
            MemberWallet myWallet = this.getMyWallet(memberWalletLog.getUid(), memberWalletLog.getCurrency());
            //只有增加资产时才会创建资产不加资产时不会创建
            if (ObjectUtil.isNull(myWallet)) {
                myWallet = new MemberWallet();
                myWallet.setBalance(BigDecimal.ZERO);
                myWallet.setFrozenBalance(BigDecimal.ZERO);
                myWallet.setCurrency(memberWalletLog.getCurrency());
                myWallet.setUid(memberWalletLog.getUid());
                log.info("用户：{}，没有{}资产账户创建资产", memberWalletLog.getUid(), memberWalletLog.getCurrency());
                this.save(myWallet);
            }
            MemberWallet memberWalletParam = new MemberWallet();
            memberWalletParam.setId(myWallet.getId());
            memberWalletParam.setVersion(myWallet.getVersion());
            //修改资产大于0 时进行修改
            if (ObjectUtil.isNotNull(memberWalletLog.getAmount()) && !NumberUtil.equals(memberWalletLog.getAmount(), BigDecimal.ZERO)) {
                log.info("用户：{}，修改{}可用资产{}", memberWalletLog.getUid(), memberWalletLog.getCurrency(), memberWalletLog.getAmount());
                BigDecimal balance = myWallet.getBalance().add(memberWalletLog.getAmount());
                if (balance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BadRequestException("账户可用余额不足!");
                }
                //修改可用余额
                memberWalletParam.setBalance(myWallet.getBalance().add(memberWalletLog.getAmount()));
                memberWalletLog.setAfterAmount(balance);
                memberWalletLog.setAmount(memberWalletLog.getAmount());
                memberWalletLog.setBeforeAmount(myWallet.getBalance());
                memberWalletLogService.save(memberWalletLog);
            }
            //修改冻结资产大于0 时进行修改
            if (ObjectUtil.isNotNull(memberWalletLog.getFrozenBalance()) && !NumberUtil.equals(memberWalletLog.getFrozenBalance(), BigDecimal.ZERO)) {
                log.info("用户：{}，修改{}冻结资产{}", memberWalletLog.getUid(), memberWalletLog.getCurrency(), memberWalletLog.getAmount());
                BigDecimal frozenBalance = myWallet.getFrozenBalance().add(memberWalletLog.getFrozenBalance());
                if (frozenBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BadRequestException("账户冻结资产余额不足!");
                }
                //修改冻结  bu添加记录
                memberWalletParam.setFrozenBalance(myWallet.getFrozenBalance().add(memberWalletLog.getFrozenBalance()));
         /*
                memberWalletLog.setAfterAmount(frozenBalance);
                memberWalletLog.setAmount(memberWalletLog.getFrozenBalance());
                memberWalletLog.setBeforeAmount(myWallet.getBalance());
                memberWalletLog.setLogType(memberWalletLog.getLogType());
                memberWalletLog.setComment("冻结-" + memberWalletLog.getComment());
                memberWalletLogService.save(memberWalletLog);*/
            }
            if (!this.updateById(memberWalletParam)) {
                throw new BadRequestException("网络异常，请稍后再试!");
            }
        } finally {
            redisUtils.del(RedisConstant.ACCOUNT_LOCK + memberWalletLog.getUid());
        }
    }

    @Override
    public MemberWallet getMyWallet(Long uid, String currency) {
        return this.query().eq("uid", uid).eq("currency", currency).one();
    }
}
