package com.qkbus.modules.mine.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.mine.domain.MineRecord;
import com.qkbus.modules.mine.service.dto.MineRecordDto;
import com.qkbus.modules.mine.service.dto.MineRecordQueryCriteria;
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
public interface MineRecordService extends BaseService<MineRecord> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(MineRecordQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<MineRecordDto>
     */
    List<MineRecord> queryAll(MineRecordQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MineRecordDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return MineRecordDto
     */
    void create(MineRecord resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(MineRecord resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Integer> ids);

}
