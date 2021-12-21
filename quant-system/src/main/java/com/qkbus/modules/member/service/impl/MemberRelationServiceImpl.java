
package com.qkbus.modules.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.domain.MemberRelation;
import com.qkbus.modules.member.service.MemberRelationService;
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.modules.member.service.dto.MemberRelationDto;
import com.qkbus.modules.member.service.dto.MemberRelationQueryCriteria;
import com.qkbus.modules.member.service.mapper.MemberRelationMapper;
import com.qkbus.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MemberRelationServiceImpl extends BaseServiceImpl<MemberRelationMapper, MemberRelation> implements MemberRelationService {

    private final IGenerator generator;
    private final MemberService memberService;

    private final MemberRelationMapper memberRelationMapper;

    @Override

    public Map<String, Object> queryAll(MemberRelationQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<MemberRelationDto> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MemberRelationDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<MemberRelationDto> queryAll(MemberRelationQueryCriteria bo) {
        Long memberId = 0L;
        if (!Objects.isNull(bo.getParentId())) {
            Member memberByUid = memberService.getMemberByUid(bo.getParentId());
            if (Objects.isNull(memberByUid)) {
                throw new BadRequestException("用户不存在");
            } else {
                memberId = memberByUid.getId();
            }
        } else if (!Objects.isNull(bo.getMemberId())) {
            Member memberByUid = memberService.getMemberByUid(bo.getMemberId());
            if (Objects.isNull(memberByUid)) {
                throw new BadRequestException("用户不存在");
            } else {
                memberId = memberByUid.getId();
            }
        } else {
            return new ArrayList<>();
        }
        QueryWrapper<MemberRelation> queryWrapper = new QueryWrapper<MemberRelation>();
        if (!Objects.isNull(bo.getParentId())) {
            queryWrapper.select("id,member_id,generation,gmt_create");
            queryWrapper.eq("parent_id", memberId);
        }
        if (!Objects.isNull(bo.getMemberId())) {
            queryWrapper.select("id,parent_id as member_id,generation,gmt_create");
            queryWrapper.eq("member_id", memberId);
        }
        List<MemberRelation> memberRelations = this.list(queryWrapper);
        return getMemberInfo(memberRelations);
    }


    @Override
    public void download(List<MemberRelationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberRelationDto MemberRelation : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", MemberRelation.getUid());
            map.put("账户", MemberRelation.getAccount());
            map.put("等级", MemberRelation.getGrade());
            map.put("第几代", MemberRelation.getGeneration());
            map.put("最大代数", MemberRelation.getMaxGeneration());
            map.put("直推人数", MemberRelation.getInviteCount());
            map.put("团队人数", MemberRelation.getTeamCount());
            map.put("注册时间", MemberRelation.getRegDate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    public List<MemberRelationDto> getMemberInfo(List<MemberRelation> memberRelations) {
        List<MemberRelationDto> list = new ArrayList<>();
        Map<Long, Member> mapUser = new HashMap();
        if (memberRelations.size() > 0) {
            List<Long> listUserId = memberRelations.stream().map(tt -> tt.getMemberId()).collect(Collectors.toList());
            List<Member> listMember = (List<Member>) memberService.listByIds(listUserId);
            mapUser = listMember.stream().collect(Collectors.toMap(Member::getId, Member -> Member));
            List<MemberRelation> relationMaxByParentIds = memberRelationMapper.getRelationMaxByParentIds(listUserId);
            Map<Long, Integer> collectSon = relationMaxByParentIds.stream().collect(Collectors.toMap(MemberRelation::getParentId, MemberRelation::getGeneration));
            for (MemberRelation e : memberRelations) {
                Member sonUser = mapUser.get(e.getMemberId());
                if (Objects.isNull(sonUser)) {
                    log.error("用户{}没有获取的个人信息，账号异常", e.getMemberId());
                    continue;
                }
                MemberRelationDto eraResp = new MemberRelationDto();
                eraResp.setId(e.getId());
                eraResp.setAccount(sonUser.getAccount());
                eraResp.setUid(sonUser.getUid());
                eraResp.setGrade(sonUser.getGrade());
                eraResp.setInviteCount(sonUser.getInviteCount());
                if (Objects.isNull(collectSon.get(e.getMemberId()))) {
                    eraResp.setMaxGeneration(0);
                } else {
                    eraResp.setMaxGeneration(collectSon.get(e.getMemberId()));
                }
                eraResp.setTeamCount(sonUser.getTeamCount());
                eraResp.setRegDate(sonUser.getGmtCreate());
                //我的子代
                eraResp.setGeneration(e.getGeneration());
                list.add(eraResp);
            }
        }
        return list;
    }
}
