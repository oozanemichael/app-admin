
package com.qkbus.modules.wallet.common.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.wallet.common.domain.CollectTask;
import com.qkbus.modules.wallet.common.service.dto.CollectTaskDto;
import com.qkbus.modules.wallet.common.service.dto.CollectTaskQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
public interface CollectTaskService extends BaseService<CollectTask> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(CollectTaskQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <CollectTaskDto>
     */
    List<CollectTask> queryAll(CollectTaskQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<CollectTaskDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return CollectTaskDto
     */
    void create(CollectTask resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(CollectTask resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);

}
