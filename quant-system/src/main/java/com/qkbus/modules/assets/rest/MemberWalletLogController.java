
package com.qkbus.modules.assets.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.assets.domain.MemberWalletLog;
import com.qkbus.modules.assets.service.MemberWalletLogService;
import com.qkbus.modules.assets.service.dto.MemberWalletLogDto;
import com.qkbus.modules.assets.service.dto.MemberWalletLogQueryCriteria;
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
 * @date 2021-05-11
 */
@AllArgsConstructor
@Api(tags = "资产日志管理")
@RestController
@RequestMapping("/api/MemberWalletLog")
public class MemberWalletLogController {

    private final MemberWalletLogService MemberWalletLogService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','MemberWalletLog:list')")
    public void download(HttpServletResponse response, MemberWalletLogQueryCriteria criteria) throws IOException {
        MemberWalletLogService.download(generator.convert(MemberWalletLogService.queryAll(criteria), MemberWalletLogDto.class), response);
    }

    @GetMapping
    @Log("查询资产日志")
    @ApiOperation("查询资产日志")
    @PreAuthorize("@el.check('admin','MemberWalletLog:list')")
    public ResponseEntity<Object> getMemberWalletLogs(MemberWalletLogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(MemberWalletLogService.queryAll(criteria, pageable), HttpStatus.OK);
    }


}