
package com.qkbus.modules.member.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.modules.member.service.dto.MemberDto;
import com.qkbus.modules.member.service.dto.MemberGradeDto;
import com.qkbus.modules.member.service.dto.MemberQueryCriteria;
import com.qkbus.modules.member.service.mapper.MemberMapper;
import com.qkbus.modules.system.domain.DictDetail;
import com.qkbus.modules.system.service.DictDetailService;
import com.qkbus.modules.system.service.dto.DictDetailQueryCriteria;
import com.qkbus.modules.vo.StatisticalVo;
import com.qkbus.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements MemberService {

    private final IGenerator generator;

    private final DictDetailService dictDetailService;

    @Override
    public Map<String, Object> queryAll(MemberQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Member> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MemberDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<Member> queryAll(MemberQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Member.class, criteria));
    }


    @Override
    public void download(List<MemberDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberDto Member : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", Member.getUid());
            map.put("会员账号", Member.getAccount());
            map.put("会员昵称", Member.getNickName());
            map.put("绑定邮箱", Member.getEmail());
            map.put("手机号", Member.getPhone());
            map.put("会员头像", Member.getAvatar());
            map.put("邀请码", Member.getInviteCode());
            map.put("个人签名", Member.getPersonSign());
            map.put("会员状态", Member.getStatus());
            map.put("直推人数", Member.getInviteCount());
            map.put("团队人数", Member.getTeamCount());
            map.put("有效直推人数", Member.getValidInviteCount());
            map.put("有效团队人数", Member.getValidTeamCount());
            map.put("创建时间", Member.getGmtCreate());
            map.put("设置等级", Member.getAdminGrade());
            map.put("用户等级", Member.getGrade());
            map.put("修改时间", Member.getGmtUpdated());
            map.put("是否有效", Member.getValidStatus());
            map.put("试用期", Member.getIsTrial());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Member resources) {
        Member member = this.getById(resources.getId());
        if (!Objects.isNull(resources.getPwd())) {
            String tempPwd = SecureUtil.md5(resources.getPwd() + member.getSalt());
            String finalPwd = SecureUtil.md5(tempPwd);
            // 默认密码 123456
            resources.setPassword(finalPwd);
        }
        if (!Objects.isNull(resources.getPayPwd())) {
            String tempPwd = SecureUtil.md5(resources.getPayPwd() + member.getSalt());
            String finalPwd = SecureUtil.md5(tempPwd);
            resources.setPayPassword(finalPwd);
        }
        this.updateById(resources);
    }


    @Override
    public Member getMemberByUid(Long uid) {
        return this.query().eq("uid", uid).one();
    }

    @Override
    public Map<String, Object> statisticalMember() {
        //统计注册总人数
        int count = this.count();
        Date beginTime = DateUtil.beginOfDay(DateUtil.date());
        Date endTime = DateUtil.endOfDay(DateUtil.date());
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.between("gmt_create", beginTime, endTime);
        int day = this.count(queryWrapper);
        List<StatisticalVo> members = baseMapper.statisticalWeek();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("day", day);
        map.put("record", members);
        return map;
    }

    @Override
    public List<MemberGradeDto> statisticalMemberGrade() {
        //获取配置的会员等级
        DictDetailQueryCriteria dictDetailQueryCriteria = new DictDetailQueryCriteria();
        dictDetailQueryCriteria.setDictName("grade");
        List<DictDetail> dictDetails = dictDetailService.queryAll(dictDetailQueryCriteria);
        List<MemberGradeDto> memberGradeDtos = new ArrayList<>();
        for (DictDetail d : dictDetails) {
            MemberGradeDto memberGradeDto = new MemberGradeDto();
            memberGradeDto.setGrade(Integer.parseInt(d.getValue()));
            memberGradeDto.setGradeName(d.getLabel());
            memberGradeDtos.add(memberGradeDto);
        }
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.select("count(grade)  as gradeNum,grade");
        queryWrapper.groupBy("grade");
        //统计等级人数
        List<Member> members = this.list(queryWrapper);
        Map<Integer, Integer> collect = members.stream().collect(Collectors.toMap(Member::getGrade, Member::getGradeNum, (k1, k2) -> k1));
        for (MemberGradeDto d : memberGradeDtos) {
            d.setGradeCount(collect.get(d.getGrade()) == null ? 0 : collect.get(d.getGrade()));
        }
        return memberGradeDtos;
    }
}
