
package com.qkbus.modules.wallet.common.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.wallet.common.domain.Config;
import com.qkbus.modules.wallet.common.service.ConfigService;
import com.qkbus.modules.wallet.common.service.dto.ConfigDto;
import com.qkbus.modules.wallet.common.service.dto.ConfigQueryCriteria;
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
@Api(tags = "波场配置管理")
@RestController
@RequestMapping("/api/wallet/Config")
public class ConfigController {

    private final ConfigService configService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','Config:list')")
    public void download(HttpServletResponse response, ConfigQueryCriteria criteria) throws IOException {
        configService.download(generator.convert(configService.queryAll(criteria), ConfigDto.class), response);
    }

    @GetMapping
    @Log("查询波场配置")
    @ApiOperation("查询波场配置")
    @PreAuthorize("@el.check('admin','Config:list')")
    public ResponseEntity<Object> getConfigs(ConfigQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(configService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增波场配置")
    @ApiOperation("新增波场配置")
    @PreAuthorize("@el.check('admin','Config:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Config resources) {
        configService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改波场配置")
    @ApiOperation("修改波场配置")
    @PreAuthorize("@el.check('admin','Config:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Config resources) {
        configService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除波场配置")
    @ApiOperation("删除波场配置")
    @PreAuthorize("@el.check('admin','Config:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        configService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}