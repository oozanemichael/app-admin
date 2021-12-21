
package com.qkbus.modules.wallet.tron.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.wallet.tron.domain.TrxAddress;
import com.qkbus.modules.wallet.tron.service.dto.TrxAddressDto;
import com.qkbus.modules.wallet.tron.service.dto.TrxAddressQueryCriteria;
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
@Api(tags = "波场地址管理")
@RestController
@RequestMapping("/api/TrxAddress")
public class TrxAddressController {

    private final com.qkbus.modules.wallet.tron.service.TrxAddressService TrxAddressService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','TrxAddress:list')")
    public void download(HttpServletResponse response, TrxAddressQueryCriteria criteria) throws IOException {
        TrxAddressService.download(generator.convert(TrxAddressService.queryAll(criteria), TrxAddressDto.class), response);
    }

    @GetMapping
    @Log("查询波场地址")
    @ApiOperation("查询波场地址")
    @PreAuthorize("@el.check('admin','TrxAddress:list')")
    public ResponseEntity<Object> getTrxAddresss(TrxAddressQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(TrxAddressService.queryAll(criteria, pageable), HttpStatus.OK);
    }


}