
package com.qkbus.modules.transfer.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.transfer.domain.TransferRecord;
import com.qkbus.modules.transfer.service.TransferRecordService;
import com.qkbus.modules.transfer.service.dto.TransferRecordDto;
import com.qkbus.modules.transfer.service.dto.TransferRecordQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@AllArgsConstructor
@Api(tags = "划转管理管理")
@RestController
@RequestMapping("/api/TransferRecord")
public class TransferRecordController {

    private final TransferRecordService TransferRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','TransferRecord:list')")
    public void download(HttpServletResponse response, TransferRecordQueryCriteria criteria) throws IOException {
        TransferRecordService.download(generator.convert(TransferRecordService.queryAll(criteria), TransferRecordDto.class), response);
    }

    @GetMapping
    @Log("查询划转管理")
    @ApiOperation("查询划转管理")
    @PreAuthorize("@el.check('admin','TransferRecord:list')")
    public ResponseEntity<Object> getTransferRecords(TransferRecordQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(TransferRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("删除划转管理")
    @ApiOperation("删除划转管理")
    @PreAuthorize("@el.check('admin','TransferRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        TransferRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}