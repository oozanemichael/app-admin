
package com.qkbus.modules.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.domain.MemberRanking;
import com.qkbus.modules.member.service.MemberRankingService;
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.modules.member.service.dto.MemberRankingDto;
import com.qkbus.modules.member.service.dto.MemberRankingQueryCriteria;
import com.qkbus.modules.member.service.mapper.MemberRankingMapper;
import com.qkbus.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MemberRankingServiceImpl extends BaseServiceImpl<MemberRankingMapper, MemberRanking> implements MemberRankingService {

    private final IGenerator generator;
    private final MemberService memberService;

    @Override
    public Map<String, Object> queryAll(MemberRankingQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<MemberRanking> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MemberRankingDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<MemberRanking> queryAll(MemberRankingQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(MemberRanking.class, criteria));
    }


    @Override
    public void download(List<MemberRankingDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberRankingDto MemberRanking : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("uid", MemberRanking.getUid());
            map.put("收益", MemberRanking.getRevenue());
            map.put("类型", MemberRanking.getType());
            map.put("创建时间", MemberRanking.getGmtCreate());
            map.put("修改时间", MemberRanking.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MemberRanking resources) {
        Member memberByUid = memberService.getMemberByUid(resources.getUid());
        if (ObjectUtil.isNull(memberByUid)) {
            throw new BadRequestException("UID:" + resources.getUid() + ",不存在,请核对后再试!");
        }
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MemberRanking resources) {
        Member memberByUid = memberService.getMemberByUid(resources.getUid());
        if (ObjectUtil.isNull(memberByUid)) {
            throw new BadRequestException("UID:" + resources.getUid() + ",不存在,请核对后再试!");
        }
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Integer> ids) {
        this.removeByIds(ids);
    }
}
