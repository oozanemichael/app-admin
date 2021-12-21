
package com.qkbus.modules.transfer.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.transfer.domain.TransferRecord;
import com.qkbus.modules.transfer.service.dto.TransferRecordDto;
import com.qkbus.modules.transfer.service.dto.TransferRecordQueryCriteria;
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
public interface TransferRecordService extends BaseService<TransferRecord> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(TransferRecordQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <TransferRecordDto>
     */
    List<TransferRecord> queryAll(TransferRecordQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<TransferRecordDto> all, HttpServletResponse response) throws IOException;


    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);

}
