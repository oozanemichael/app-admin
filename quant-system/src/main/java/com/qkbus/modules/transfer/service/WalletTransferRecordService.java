package com.qkbus.modules.transfer.service;
import com.qkbus.common.service.BaseService;
import com.qkbus.modules.transfer.domain.WalletTransferRecord;
import com.qkbus.modules.transfer.service.dto.WalletTransferRecordDto;
import com.qkbus.modules.transfer.service.dto.WalletTransferRecordQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
* @author 少林一枝花
* @date 2021-07-05
*/
public interface WalletTransferRecordService extends BaseService<WalletTransferRecord>{

/**
* 查询数据分页
* @param criteria 条件
* @param pageable 分页参数
* @return Map
<String,Object>
*/
Map
<String,Object> queryAll(WalletTransferRecordQueryCriteria criteria, Pageable pageable);

/**
* 查询所有数据不分页
* @param criteria 条件参数
* @return List
<WalletTransferRecordDto>
    */
    List<WalletTransferRecord> queryAll(WalletTransferRecordQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List
    <WalletTransferRecordDto> all, HttpServletResponse response) throws IOException;

        /**
        * 创建
        * @param resources /
        * @return WalletTransferRecordDto
        */
        void create(WalletTransferRecord resources);

        /**
        * 编辑
        * @param resources /
        */
        void update(WalletTransferRecord resources);

        /**
        * 多选删除
        * @param ids /
        */
        void deleteAll( Set<Long> ids);

        }
