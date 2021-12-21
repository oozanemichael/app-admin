package com.qkbus.modules.mine.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.mine.domain.MineOrders;
import com.qkbus.modules.mine.service.MineOrdersService;
import com.qkbus.modules.mine.service.dto.MineOrdersQueryCriteria;
import com.qkbus.modules.mine.service.dto.MineOrdersDto;
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
@Api(tags = "矿机订单管理")
@RestController
@RequestMapping("/api/mineOrders")
public class MineOrdersController {

    private final MineOrdersService mineOrdersService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','mineOrders:list')")
    public void download(HttpServletResponse response, MineOrdersQueryCriteria criteria) throws IOException {
        mineOrdersService.download(generator.convert(mineOrdersService.queryAll(criteria), MineOrdersDto.class), response);
    }

    @GetMapping
    @Log("查询矿机订单")
    @ApiOperation("查询矿机订单")
    @PreAuthorize("@el.check('admin','mineOrders:list')")
    public ResponseEntity<Object> getMineOrderss(MineOrdersQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(mineOrdersService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增矿机订单")
    @ApiOperation("新增矿机订单")
    @PreAuthorize("@el.check('admin','mineOrders:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MineOrders resources) {
        mineOrdersService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改矿机订单")
    @ApiOperation("修改矿机订单")
    @PreAuthorize("@el.check('admin','mineOrders:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MineOrders resources) {
        mineOrdersService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除矿机订单")
    @ApiOperation("删除矿机订单")
    @PreAuthorize("@el.check('admin','mineOrders:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Long> ids) {
        mineOrdersService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}