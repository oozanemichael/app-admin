package com.qkbus.modules.deal.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.deal.domain.CurrencyPair;
import com.qkbus.modules.deal.service.CurrencyPairService;
import com.qkbus.modules.deal.service.dto.CurrencyPairQueryCriteria;
import com.qkbus.modules.deal.service.dto.CurrencyPairDto;
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
@Api(tags = "币种管理管理")
@RestController
@RequestMapping("/api/currencyPair")
public class CurrencyPairController {

    private final CurrencyPairService currencyPairService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','currencyPair:list')")
    public void download(HttpServletResponse response, CurrencyPairQueryCriteria criteria) throws IOException {
        currencyPairService.download(generator.convert(currencyPairService.queryAll(criteria), CurrencyPairDto.class), response);
    }

    @GetMapping
    @Log("查询币种管理")
    @ApiOperation("查询币种管理")
    @PreAuthorize("@el.check('admin','currencyPair:list')")
    public ResponseEntity<Object> getCurrencyPairs(CurrencyPairQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(currencyPairService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增币种管理")
    @ApiOperation("新增币种管理")
    @PreAuthorize("@el.check('admin','currencyPair:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CurrencyPair resources) {
        currencyPairService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改币种管理")
    @ApiOperation("修改币种管理")
    @PreAuthorize("@el.check('admin','currencyPair:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CurrencyPair resources) {
        currencyPairService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除币种管理")
    @ApiOperation("删除币种管理")
    @PreAuthorize("@el.check('admin','currencyPair:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Integer> ids) {
        currencyPairService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}