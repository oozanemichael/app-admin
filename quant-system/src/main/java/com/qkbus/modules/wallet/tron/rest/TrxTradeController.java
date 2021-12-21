
package com.qkbus.modules.wallet.tron.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.wallet.tron.domain.TrxTrade;
import com.qkbus.modules.wallet.tron.service.dto.TrxTradeDto;
import com.qkbus.modules.wallet.tron.service.dto.TrxTradeQueryCriteria;
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
 * @date 2021-05-15
 */
@AllArgsConstructor
@Api(tags = "波场交易管理")
@RestController
@RequestMapping("/api/TrxTrade")
public class TrxTradeController {

    private final com.qkbus.modules.wallet.tron.service.TrxTradeService TrxTradeService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','TrxTrade:list')")
    public void download(HttpServletResponse response, TrxTradeQueryCriteria criteria) throws IOException {
        TrxTradeService.download(generator.convert(TrxTradeService.queryAll(criteria), TrxTradeDto.class), response);
    }

    @GetMapping
    @Log("查询波场交易")
    @ApiOperation("查询波场交易")
    @PreAuthorize("@el.check('admin','TrxTrade:list')")
    public ResponseEntity<Object> getTrxTrades(TrxTradeQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(TrxTradeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PutMapping
    @Log("修改波场交易")
    @ApiOperation("修改波场交易")
    @PreAuthorize("@el.check('admin','TrxTrade:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TrxTrade resources) {
        TrxTradeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}