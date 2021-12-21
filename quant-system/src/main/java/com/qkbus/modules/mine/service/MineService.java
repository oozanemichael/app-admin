package com.qkbus.modules.mine.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.mine.domain.Mine;
import com.qkbus.modules.mine.service.dto.MineDto;
import com.qkbus.modules.mine.service.dto.MineQueryCriteria;
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
public interface MineService extends BaseService<Mine> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(MineQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<MineDto>
     */
    List<Mine> queryAll(MineQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MineDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return MineDto
     */
    void create(Mine resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Mine resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Integer> ids);

}
