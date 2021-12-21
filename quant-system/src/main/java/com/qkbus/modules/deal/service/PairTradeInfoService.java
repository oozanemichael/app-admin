package com.qkbus.modules.deal.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.deal.domain.PairTradeInfo;
import com.qkbus.modules.deal.service.dto.PairTradeInfoDto;
import com.qkbus.modules.deal.service.dto.PairTradeInfoQueryCriteria;
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
public interface PairTradeInfoService extends BaseService<PairTradeInfo> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map
            <String, Object> queryAll(PairTradeInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <PairTradeInfoDto>
     */
    List<PairTradeInfo> queryAll(PairTradeInfoQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List
                          <PairTradeInfoDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return PairTradeInfoDto
     */
    void create(PairTradeInfo resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(PairTradeInfo resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);

}
