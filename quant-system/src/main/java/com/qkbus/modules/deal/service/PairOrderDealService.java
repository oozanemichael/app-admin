package com.qkbus.modules.deal.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.deal.domain.PairOrderDeal;
import com.qkbus.modules.deal.service.dto.PairOrderDealDto;
import com.qkbus.modules.deal.service.dto.PairOrderDealQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
public interface PairOrderDealService extends BaseService<PairOrderDeal> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map
            <String, Object> queryAll(PairOrderDealQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <PairOrderDealDto>
     */
    List<PairOrderDeal> queryAll(PairOrderDealQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List
                          <PairOrderDealDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return PairOrderDealDto
     */
    void create(PairOrderDeal resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(PairOrderDeal resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);

}
