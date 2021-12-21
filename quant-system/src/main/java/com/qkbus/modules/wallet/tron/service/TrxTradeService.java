
package com.qkbus.modules.wallet.tron.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.wallet.tron.domain.TrxTrade;
import com.qkbus.modules.wallet.tron.service.dto.TrxTradeDto;
import com.qkbus.modules.wallet.tron.service.dto.TrxTradeQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
public interface TrxTradeService extends BaseService<TrxTrade> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(TrxTradeQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <TrxTradeDto>
     */
    List<TrxTrade> queryAll(TrxTradeQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<TrxTradeDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return TrxTradeDto
     */
    void create(TrxTrade resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(TrxTrade resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);

}
