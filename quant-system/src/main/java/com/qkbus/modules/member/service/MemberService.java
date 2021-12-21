
package com.qkbus.modules.member.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.service.dto.MemberDto;
import com.qkbus.modules.member.service.dto.MemberGradeDto;
import com.qkbus.modules.member.service.dto.MemberQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
public interface MemberService extends BaseService<Member> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(MemberQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <MemberDto>
     */
    List<Member> queryAll(MemberQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MemberDto> all, HttpServletResponse response) throws IOException;

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Member resources);


    Member getMemberByUid(Long uid);

    //统计会员人数
    Map<String, Object> statisticalMember();

    /*
     * 统计会员数据
     * */

    List<MemberGradeDto> statisticalMemberGrade();


}
