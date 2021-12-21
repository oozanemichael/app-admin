package com.qkbus.modules.deal.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.deal.domain.PairOrderDeal;
import com.qkbus.modules.deal.service.PairOrderDealService;
import com.qkbus.modules.deal.service.dto.PairOrderDealQueryCriteria;
import com.qkbus.modules.deal.service.dto.PairOrderDealDto;
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
@Api(tags = "交易订单管理")
@RestController
@RequestMapping("/api/pairOrderDeal")
public class PairOrderDealController {

    private final PairOrderDealService pairOrderDealService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','pairOrderDeal:list')")
    public void download(HttpServletResponse response, PairOrderDealQueryCriteria criteria) throws IOException {
        pairOrderDealService.download(generator.convert(pairOrderDealService.queryAll(criteria), PairOrderDealDto.class), response);
    }

    @GetMapping
    @Log("查询交易订单")
    @ApiOperation("查询交易订单")
    @PreAuthorize("@el.check('admin','pairOrderDeal:list')")
    public ResponseEntity
            <Object> getPairOrderDeals(PairOrderDealQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(pairOrderDealService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增交易订单")
    @ApiOperation("新增交易订单")
    @PreAuthorize("@el.check('admin','pairOrderDeal:add')")
    public ResponseEntity
            <Object> create(@Validated @RequestBody PairOrderDeal resources) {
        pairOrderDealService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改交易订单")
    @ApiOperation("修改交易订单")
    @PreAuthorize("@el.check('admin','pairOrderDeal:edit')")
    public ResponseEntity
            <Object> update(@Validated @RequestBody PairOrderDeal resources) {
        pairOrderDealService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除交易订单")
    @ApiOperation("删除交易订单")
    @PreAuthorize("@el.check('admin','pairOrderDeal:del')")
    @DeleteMapping
    public ResponseEntity
            <Object> deleteAll(@RequestBody Set<Long> ids) {
        pairOrderDealService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}