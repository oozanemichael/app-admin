
package com.qkbus.modules.transfer.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.transfer.domain.TransferConfig;
import com.qkbus.modules.transfer.service.TransferConfigService;
import com.qkbus.modules.transfer.service.dto.TransferConfigDto;
import com.qkbus.modules.transfer.service.dto.TransferConfigQueryCriteria;
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
@Api(tags = "划转配置管理")
@RestController
@RequestMapping("/api/TransferConfig")
public class TransferConfigController {

    private final TransferConfigService TransferConfigService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','TransferConfig:list')")
    public void download(HttpServletResponse response, TransferConfigQueryCriteria criteria) throws IOException {
        TransferConfigService.download(generator.convert(TransferConfigService.queryAll(criteria), TransferConfigDto.class), response);
    }

    @GetMapping
    @Log("查询划转配置")
    @ApiOperation("查询划转配置")
    @PreAuthorize("@el.check('admin','TransferConfig:list')")
    public ResponseEntity<Object> getTransferConfigs(TransferConfigQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(TransferConfigService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增划转配置")
    @ApiOperation("新增划转配置")
    @PreAuthorize("@el.check('admin','TransferConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody TransferConfig resources) {
        TransferConfigService.save(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改划转配置")
    @ApiOperation("修改划转配置")
    @PreAuthorize("@el.check('admin','TransferConfig:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TransferConfig resources) {
        TransferConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除划转配置")
    @ApiOperation("删除划转配置")
    @PreAuthorize("@el.check('admin','TransferConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        TransferConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}