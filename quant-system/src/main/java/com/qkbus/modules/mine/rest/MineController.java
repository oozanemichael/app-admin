package com.qkbus.modules.mine.rest;

import java.util.Arrays;

import com.qkbus.dozer.service.IGenerator;
import lombok.AllArgsConstructor;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.mine.domain.Mine;
import com.qkbus.modules.mine.service.MineService;
import com.qkbus.modules.mine.service.dto.MineQueryCriteria;
import com.qkbus.modules.mine.service.dto.MineDto;
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
@Api(tags = "矿机管理")
@RestController
@RequestMapping("/api/mine")
public class MineController {

    private final MineService mineService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','mine:list')")
    public void download(HttpServletResponse response, MineQueryCriteria criteria) throws IOException {
        mineService.download(generator.convert(mineService.queryAll(criteria), MineDto.class), response);
    }

    @GetMapping
    @Log("查询矿机")
    @ApiOperation("查询矿机")
    @PreAuthorize("@el.check('admin','mine:list')")
    public ResponseEntity<Object> getMines(MineQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(mineService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增矿机")
    @ApiOperation("新增矿机")
    @PreAuthorize("@el.check('admin','mine:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Mine resources) {
        mineService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改矿机")
    @ApiOperation("修改矿机")
    @PreAuthorize("@el.check('admin','mine:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Mine resources) {
        mineService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除矿机")
    @ApiOperation("删除矿机")
    @PreAuthorize("@el.check('admin','mine:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Integer> ids) {
        mineService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}