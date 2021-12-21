
package com.qkbus.modules.recharge.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.recharge.domain.Recharge;
import com.qkbus.modules.recharge.service.RechargeService;
import com.qkbus.modules.recharge.service.dto.RechargeDto;
import com.qkbus.modules.recharge.service.dto.RechargeQueryCriteria;
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
@Api(tags = "充值管理管理")
@RestController
@RequestMapping("/api/Recharge")
public class RechargeController {

    private final RechargeService RechargeService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','Recharge:list')")
    public void download(HttpServletResponse response, RechargeQueryCriteria criteria) throws IOException {
        RechargeService.download(generator.convert(RechargeService.queryAll(criteria), RechargeDto.class), response);
    }

    @GetMapping
    @Log("查询充值管理")
    @ApiOperation("查询充值管理")
    @PreAuthorize("@el.check('admin','Recharge:list')")
    public ResponseEntity<Object> getRecharges(RechargeQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(RechargeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增充值管理")
    @ApiOperation("新增充值管理")
    @PreAuthorize("@el.check('admin','Recharge:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Recharge resources) {
        RechargeService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("删除充值管理")
    @ApiOperation("删除充值管理")
    @PreAuthorize("@el.check('admin','Recharge:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        RechargeService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("统计充值")
    @ApiOperation("统计充值")
    @GetMapping("/statistical")
    public ResponseEntity<Object> statisticalRecharge() {
        return new ResponseEntity<>(RechargeService.statisticalRecharge(), HttpStatus.OK);
    }


}