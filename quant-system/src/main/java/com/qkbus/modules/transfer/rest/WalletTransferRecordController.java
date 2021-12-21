package com.qkbus.modules.transfer.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.transfer.domain.WalletTransferRecord;
import com.qkbus.modules.transfer.service.WalletTransferRecordService;
import com.qkbus.modules.transfer.service.dto.WalletTransferRecordQueryCriteria;
import com.qkbus.modules.transfer.service.dto.WalletTransferRecordDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 少林一枝花
 * @date 2021-07-05
 */
@AllArgsConstructor
@Api(tags = "划转管理")
@RestController
@RequestMapping("/api/walletTransferRecord")
public class WalletTransferRecordController {

    private final WalletTransferRecordService walletTransferRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','walletTransferRecord:list')")
    public void download(HttpServletResponse response, WalletTransferRecordQueryCriteria criteria) throws IOException {
        walletTransferRecordService.download(generator.convert(walletTransferRecordService.queryAll(criteria), WalletTransferRecordDto.class), response);
    }

    @GetMapping
    @Log("查询划转")
    @ApiOperation("查询划转")
    @PreAuthorize("@el.check('admin','walletTransferRecord:list')")
    public ResponseEntity
            <Object> getWalletTransferRecords(WalletTransferRecordQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(walletTransferRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增划转")
    @ApiOperation("新增划转")
    @PreAuthorize("@el.check('admin','walletTransferRecord:add')")
    public ResponseEntity
            <Object> create(@Validated @RequestBody WalletTransferRecord resources) {
        walletTransferRecordService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改划转")
    @ApiOperation("修改划转")
    @PreAuthorize("@el.check('admin','walletTransferRecord:edit')")
    public ResponseEntity
            <Object> update(@Validated @RequestBody WalletTransferRecord resources) {
        walletTransferRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除划转")
    @ApiOperation("删除划转")
    @PreAuthorize("@el.check('admin','walletTransferRecord:del')")
    @DeleteMapping
    public ResponseEntity
            <Object> deleteAll(@RequestBody Set<Long> ids) {
        walletTransferRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}