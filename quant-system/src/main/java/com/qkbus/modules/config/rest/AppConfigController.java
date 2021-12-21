package com.qkbus.modules.config.rest;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.config.domain.AppConfig;
import com.qkbus.modules.config.service.AppConfigService;
import com.qkbus.modules.config.service.dto.AppConfigQueryCriteria;
import com.qkbus.modules.config.service.dto.AppConfigDto;
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
 * @date 2021-06-10
 */
@AllArgsConstructor
@Api(tags = "配置管理")
@RestController
@RequestMapping("/api/quant/Config")
public class AppConfigController {

    private final AppConfigService ConfigService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','QuantConfig:list')")
    public void download(HttpServletResponse response, AppConfigQueryCriteria criteria) throws IOException {
        ConfigService.download(generator.convert(ConfigService.queryAll(criteria), AppConfigDto.class), response);
    }

    @GetMapping
    @Log("查询配置")
    @ApiOperation("查询配置")
    @PreAuthorize("@el.check('admin','QuantConfig:list')")
    public ResponseEntity
            <Object> getConfigs(AppConfigQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(ConfigService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增配置")
    @ApiOperation("新增配置")
    @PreAuthorize("@el.check('admin','QuantConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody AppConfig resources) {
        ConfigService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改配置")
    @ApiOperation("修改配置")
    @PreAuthorize("@el.check('admin','QuantConfig:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody AppConfig resources) {
        ConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除配置")
    @ApiOperation("删除配置")
    @PreAuthorize("@el.check('admin','QuantConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        ConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}