package com.qkbus.modules.deal.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.deal.domain.PairTradeInfo;
import com.qkbus.modules.deal.service.PairTradeInfoService;
import com.qkbus.modules.deal.service.dto.PairTradeInfoQueryCriteria;
import com.qkbus.modules.deal.service.dto.PairTradeInfoDto;
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
@Api(tags = "每日价格管理")
@RestController
@RequestMapping("/api/pairTradeInfo")
public class PairTradeInfoController {

    private final PairTradeInfoService pairTradeInfoService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','pairTradeInfo:list')")
    public void download(HttpServletResponse response, PairTradeInfoQueryCriteria criteria) throws IOException {
        pairTradeInfoService.download(generator.convert(pairTradeInfoService.queryAll(criteria), PairTradeInfoDto.class), response);
    }

    @GetMapping
    @Log("查询每日价格")
    @ApiOperation("查询每日价格")
    @PreAuthorize("@el.check('admin','pairTradeInfo:list')")
    public ResponseEntity
            <Object> getPairTradeInfos(PairTradeInfoQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(pairTradeInfoService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增每日价格")
    @ApiOperation("新增每日价格")
    @PreAuthorize("@el.check('admin','pairTradeInfo:add')")
    public ResponseEntity
            <Object> create(@Validated @RequestBody PairTradeInfo resources) {
        pairTradeInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改每日价格")
    @ApiOperation("修改每日价格")
    @PreAuthorize("@el.check('admin','pairTradeInfo:edit')")
    public ResponseEntity
            <Object> update(@Validated @RequestBody PairTradeInfo resources) {
        pairTradeInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除每日价格")
    @ApiOperation("删除每日价格")
    @PreAuthorize("@el.check('admin','pairTradeInfo:del')")
    @DeleteMapping
    public ResponseEntity
            <Object> deleteAll(@RequestBody Set<Long> ids) {
        pairTradeInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}