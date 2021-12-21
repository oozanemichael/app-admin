
package com.qkbus.modules.member.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.member.domain.MemberRanking;
import com.qkbus.modules.member.service.dto.MemberRankingDto;
import com.qkbus.modules.member.service.dto.MemberRankingQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
public interface MemberRankingService extends BaseService<MemberRanking> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(MemberRankingQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <MemberRankingDto>
     */
    List<MemberRanking> queryAll(MemberRankingQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MemberRankingDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return MemberRankingDto
     */
    void create(MemberRanking resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(MemberRanking resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Integer> ids);

}
