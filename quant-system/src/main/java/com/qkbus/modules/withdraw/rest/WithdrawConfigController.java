
package com.qkbus.modules.withdraw.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.withdraw.domain.WithdrawConfig;
import com.qkbus.modules.withdraw.service.WithdrawConfigService;
import com.qkbus.modules.withdraw.service.dto.WithdrawConfigDto;
import com.qkbus.modules.withdraw.service.dto.WithdrawConfigQueryCriteria;
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
@Api(tags = "提币配置管理")
@RestController
@RequestMapping("/api/WithdrawConfig")
public class WithdrawConfigController {

    private final WithdrawConfigService WithdrawConfigService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','WithdrawConfig:list')")
    public void download(HttpServletResponse response, WithdrawConfigQueryCriteria criteria) throws IOException {
        WithdrawConfigService.download(generator.convert(WithdrawConfigService.queryAll(criteria), WithdrawConfigDto.class), response);
    }

    @GetMapping
    @Log("查询提币配置")
    @ApiOperation("查询提币配置")
    @PreAuthorize("@el.check('admin','WithdrawConfig:list')")
    public ResponseEntity<Object> getWithdrawConfigs(WithdrawConfigQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(WithdrawConfigService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增提币配置")
    @ApiOperation("新增提币配置")
    @PreAuthorize("@el.check('admin','WithdrawConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WithdrawConfig resources) {
        WithdrawConfigService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改提币配置")
    @ApiOperation("修改提币配置")
    @PreAuthorize("@el.check('admin','WithdrawConfig:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WithdrawConfig resources) {
        WithdrawConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除提币配置")
    @ApiOperation("删除提币配置")
    @PreAuthorize("@el.check('admin','WithdrawConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        WithdrawConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}