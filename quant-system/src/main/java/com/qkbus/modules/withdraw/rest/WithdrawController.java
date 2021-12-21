
package com.qkbus.modules.withdraw.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.withdraw.domain.Withdraw;
import com.qkbus.modules.withdraw.service.WithdrawService;
import com.qkbus.modules.withdraw.service.dto.WithdrawDto;
import com.qkbus.modules.withdraw.service.dto.WithdrawQueryCriteria;
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
@Api(tags = "提币管理管理")
@RestController
@RequestMapping("/api/Withdraw")
public class WithdrawController {

    private final WithdrawService WithdrawService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','Withdraw:list')")
    public void download(HttpServletResponse response, WithdrawQueryCriteria criteria) throws IOException {
        WithdrawService.download(generator.convert(WithdrawService.queryAll(criteria), WithdrawDto.class), response);
    }

    @GetMapping
    @Log("查询提币管理")
    @ApiOperation("查询提币管理")
    @PreAuthorize("@el.check('admin','Withdraw:list')")
    public ResponseEntity<Object> getWithdraws(WithdrawQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(WithdrawService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PutMapping
    @Log("修改提币管理")
    @ApiOperation("修改提币管理")
    @PreAuthorize("@el.check('admin','Withdraw:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Withdraw resources) {
        WithdrawService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除提币管理")
    @ApiOperation("删除提币管理")
    @PreAuthorize("@el.check('admin','Withdraw:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        WithdrawService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Log("统计提币总数")
    @ApiOperation("统计提币总数")
    @GetMapping("/statistical")
    public ResponseEntity<Object> statisticalWithdraw() {
        return new ResponseEntity<>(WithdrawService.statisticalWithdraw(), HttpStatus.OK);
    }


}