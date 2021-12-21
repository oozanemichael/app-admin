
package com.qkbus.modules.recharge.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.enums.CurrencyEnum;
import com.qkbus.enums.WalletLogEnum;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.assets.service.MemberWalletLogService;
import com.qkbus.modules.assets.service.MemberWalletService;
import com.qkbus.modules.assets.service.dto.MemberWalletLogParam;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.modules.recharge.domain.Recharge;
import com.qkbus.modules.recharge.service.RechargeService;
import com.qkbus.modules.recharge.service.dto.RechargeDto;
import com.qkbus.modules.recharge.service.dto.RechargeQueryCriteria;
import com.qkbus.modules.recharge.service.mapper.RechargeMapper;
import com.qkbus.modules.vo.StatisticalVo;
import com.qkbus.utils.FileUtil;
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
public class RechargeServiceImpl extends BaseServiceImpl<RechargeMapper, Recharge> implements RechargeService {

    private final IGenerator generator;

    private final MemberWalletLogService memberWalletLogService;

    private final MemberService memberService;

    private final MemberWalletService memberWalletService;

    @Override

    public Map<String, Object> queryAll(RechargeQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Recharge> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), RechargeDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<Recharge> queryAll(RechargeQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Recharge.class, criteria));
    }


    @Override
    public void download(List<RechargeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RechargeDto Recharge : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", Recharge.getMemberId());
            map.put("公链", Recharge.getChainType());
            map.put("充值类型", Recharge.getRechargeType());
            map.put("币种", Recharge.getCurrency());
            map.put("充币数量", Recharge.getAmount());
            map.put("充币地址", Recharge.getAddress());
            map.put("状态", Recharge.getStatus());
            map.put("创建时间", Recharge.getGmtCreate());
            map.put("修改时间", Recharge.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void create(Recharge resources) {
        //查询会员是否存在
        Member member = memberService.getMemberByUid(resources.getMemberId());
        if (ObjectUtil.isEmpty(member)) {
            throw new BadRequestException("用户UID:{},不存在!" + resources.getMemberId());
        }
        //修改可用
        MemberWalletLogParam memberWalletLog = new MemberWalletLogParam();
        memberWalletLog.setAmount(resources.getAmount());
        memberWalletLog.setUid(resources.getMemberId());
        memberWalletLog.setCurrency(resources.getCurrency());
        memberWalletLog.setLogType(WalletLogEnum.LOG_TYPE_RECHARGE.getLogType());
        memberWalletLog.setComment("充值");
        memberWalletService.updateWallet(memberWalletLog);
        resources.setStatus(1);
        resources.setRechargeType(1);
        this.save(resources);
    }


    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public Map<String, Object> statisticalRecharge() {
        //获取总提币量
        QueryWrapper<Recharge> queryWrapper = new QueryWrapper<Recharge>();
        queryWrapper.select("ifnull(sum(amount),0) as amount");
        queryWrapper.eq("currency", CurrencyEnum.USDT.getCurrency());
        Recharge one = this.getOne(queryWrapper);
        Date beginTime = DateUtil.beginOfDay(DateUtil.date());
        Date endTime = DateUtil.endOfDay(DateUtil.date());
        queryWrapper.between("gmt_create", beginTime, endTime);

        Recharge day = this.getOne(queryWrapper);
        //获取最近七天的提笔量
        List<StatisticalVo> recharges = baseMapper.statisticalWeek();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", one.getAmount());
        map.put("day", day.getAmount());
        map.put("record", recharges);
        return map;
    }
}
