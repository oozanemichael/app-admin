
package com.qkbus.modules.assets.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.assets.domain.MemberWallet;
import com.qkbus.modules.assets.service.MemberWalletService;
import com.qkbus.modules.assets.service.dto.MemberWalletDto;
import com.qkbus.modules.assets.service.dto.MemberWalletQueryCriteria;
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
@Api(tags = "会员资产管理")
@RestController
@RequestMapping("/api/MemberWallet")
public class MemberWalletController {

    private final MemberWalletService MemberWalletService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','MemberWallet:list')")
    public void download(HttpServletResponse response, MemberWalletQueryCriteria criteria) throws IOException {
        MemberWalletService.download(generator.convert(MemberWalletService.queryAll(criteria), MemberWalletDto.class), response);
    }

    @GetMapping
    @Log("查询会员资产")
    @ApiOperation("查询会员资产")
    @PreAuthorize("@el.check('admin','MemberWallet:list')")
    public ResponseEntity<Object> getMemberWallets(MemberWalletQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(MemberWalletService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员资产")
    @ApiOperation("新增会员资产")
    @PreAuthorize("@el.check('admin','MemberWallet:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MemberWallet resources) {
        MemberWalletService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改会员资产")
    @ApiOperation("修改会员资产")
    @PreAuthorize("@el.check('admin','MemberWallet:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MemberWallet resources) {
        MemberWalletService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员资产")
    @ApiOperation("删除会员资产")
    @PreAuthorize("@el.check('admin','MemberWallet:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        MemberWalletService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}