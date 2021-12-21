package com.qkbus.modules.mine.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.mine.domain.MineRecord;
import com.qkbus.modules.mine.service.MineRecordService;
import com.qkbus.modules.mine.service.dto.MineRecordQueryCriteria;
import com.qkbus.modules.mine.service.dto.MineRecordDto;
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
@Api(tags = "矿机记录管理")
@RestController
@RequestMapping("/api/mineRecord")
public class MineRecordController {

    private final MineRecordService mineRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','mineRecord:list')")
    public void download(HttpServletResponse response, MineRecordQueryCriteria criteria) throws IOException {
        mineRecordService.download(generator.convert(mineRecordService.queryAll(criteria), MineRecordDto.class), response);
    }

    @GetMapping
    @Log("查询矿机记录")
    @ApiOperation("查询矿机记录")
    @PreAuthorize("@el.check('admin','mineRecord:list')")
    public ResponseEntity<Object> getMineRecords(MineRecordQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(mineRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增矿机记录")
    @ApiOperation("新增矿机记录")
    @PreAuthorize("@el.check('admin','mineRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MineRecord resources) {
        mineRecordService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改矿机记录")
    @ApiOperation("修改矿机记录")
    @PreAuthorize("@el.check('admin','mineRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MineRecord resources) {
        mineRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除矿机记录")
    @ApiOperation("删除矿机记录")
    @PreAuthorize("@el.check('admin','mineRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Integer> ids) {
        mineRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}