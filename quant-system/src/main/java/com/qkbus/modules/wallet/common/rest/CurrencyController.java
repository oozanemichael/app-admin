
package com.qkbus.modules.wallet.common.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.wallet.common.domain.Currency;
import com.qkbus.modules.wallet.common.service.dto.CurrencyDto;
import com.qkbus.modules.wallet.common.service.dto.CurrencyQueryCriteria;
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
@Api(tags = "币种管理管理")
@RestController
@RequestMapping("/api/Currency")
public class CurrencyController {

    private final com.qkbus.modules.wallet.common.service.CurrencyService CurrencyService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','Currency:list')")
    public void download(HttpServletResponse response, CurrencyQueryCriteria criteria) throws IOException {
        CurrencyService.download(generator.convert(CurrencyService.queryAll(criteria), CurrencyDto.class), response);
    }

    @GetMapping
    @Log("查询币种管理")
    @ApiOperation("查询币种管理")
    @PreAuthorize("@el.check('admin','Currency:list')")
    public ResponseEntity<Object> getCurrencys(CurrencyQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(CurrencyService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增币种管理")
    @ApiOperation("新增币种管理")
    @PreAuthorize("@el.check('admin','Currency:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Currency resources) {
        CurrencyService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改币种管理")
    @ApiOperation("修改币种管理")
    @PreAuthorize("@el.check('admin','Currency:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Currency resources) {
        CurrencyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除币种管理")
    @ApiOperation("删除币种管理")
    @PreAuthorize("@el.check('admin','Currency:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Integer> ids) {
        CurrencyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}