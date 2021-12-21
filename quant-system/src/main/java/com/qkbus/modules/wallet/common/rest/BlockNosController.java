
package com.qkbus.modules.wallet.common.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.wallet.common.domain.BlockNos;
import com.qkbus.modules.wallet.common.service.dto.BlockNosDto;
import com.qkbus.modules.wallet.common.service.dto.BlockNosQueryCriteria;
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
@Api(tags = "区块管理管理")
@RestController
@RequestMapping("/api/BlockNos")
public class BlockNosController {

    private final com.qkbus.modules.wallet.common.service.BlockNosService BlockNosService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','BlockNos:list')")
    public void download(HttpServletResponse response, BlockNosQueryCriteria criteria) throws IOException {
        BlockNosService.download(generator.convert(BlockNosService.queryAll(criteria), BlockNosDto.class), response);
    }

    @GetMapping
    @Log("查询区块管理")
    @ApiOperation("查询区块管理")
    @PreAuthorize("@el.check('admin','BlockNos:list')")
    public ResponseEntity<Object> getBlockNoss(BlockNosQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(BlockNosService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增区块管理")
    @ApiOperation("新增区块管理")
    @PreAuthorize("@el.check('admin','BlockNos:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BlockNos resources) {
        BlockNosService.save(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改区块管理")
    @ApiOperation("修改区块管理")
    @PreAuthorize("@el.check('admin','BlockNos:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BlockNos resources) {
        BlockNosService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除区块管理")
    @ApiOperation("删除区块管理")
    @PreAuthorize("@el.check('admin','BlockNos:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        BlockNosService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}