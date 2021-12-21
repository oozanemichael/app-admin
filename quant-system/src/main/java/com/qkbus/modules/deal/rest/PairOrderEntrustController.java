package com.qkbus.modules.deal.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.deal.domain.PairOrderEntrust;
import com.qkbus.modules.deal.service.PairOrderEntrustService;
import com.qkbus.modules.deal.service.dto.PairOrderEntrustQueryCriteria;
import com.qkbus.modules.deal.service.dto.PairOrderEntrustDto;
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
 * @date 2021-07-03
 */
@AllArgsConstructor
@Api(tags = "委托单管理")
@RestController
@RequestMapping("/api/pairOrderEntrust")
public class PairOrderEntrustController {

    private final PairOrderEntrustService pairOrderEntrustService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','pairOrderEntrust:list')")
    public void download(HttpServletResponse response, PairOrderEntrustQueryCriteria criteria) throws IOException {
        pairOrderEntrustService.download(generator.convert(pairOrderEntrustService.queryAll(criteria), PairOrderEntrustDto.class), response);
    }

    @GetMapping
    @Log("查询委托单")
    @ApiOperation("查询委托单")
    @PreAuthorize("@el.check('admin','pairOrderEntrust:list')")
    public ResponseEntity
            <Object> getPairOrderEntrusts(PairOrderEntrustQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(pairOrderEntrustService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增委托单")
    @ApiOperation("新增委托单")
    @PreAuthorize("@el.check('admin','pairOrderEntrust:add')")
    public ResponseEntity
            <Object> create(@Validated @RequestBody PairOrderEntrust resources) {
        pairOrderEntrustService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改委托单")
    @ApiOperation("修改委托单")
    @PreAuthorize("@el.check('admin','pairOrderEntrust:edit')")
    public ResponseEntity
            <Object> update(@Validated @RequestBody PairOrderEntrust resources) {
        pairOrderEntrustService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除委托单")
    @ApiOperation("删除委托单")
    @PreAuthorize("@el.check('admin','pairOrderEntrust:del')")
    @DeleteMapping
    public ResponseEntity
            <Object> deleteAll(@RequestBody Set<Long> ids) {
        pairOrderEntrustService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}