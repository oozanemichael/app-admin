
package com.qkbus.modules.wallet.common.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.wallet.common.domain.CollectTask;
import com.qkbus.modules.wallet.common.service.dto.CollectTaskDto;
import com.qkbus.modules.wallet.common.service.dto.CollectTaskQueryCriteria;
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
@Api(tags = "定时管理管理")
@RestController
@RequestMapping("/api/CollectTask")
public class CollectTaskController {

    private final com.qkbus.modules.wallet.common.service.CollectTaskService CollectTaskService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','CollectTask:list')")
    public void download(HttpServletResponse response, CollectTaskQueryCriteria criteria) throws IOException {
        CollectTaskService.download(generator.convert(CollectTaskService.queryAll(criteria), CollectTaskDto.class), response);
    }

    @GetMapping
    @Log("查询定时管理")
    @ApiOperation("查询定时管理")
    @PreAuthorize("@el.check('admin','CollectTask:list')")
    public ResponseEntity<Object> getCollectTasks(CollectTaskQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(CollectTaskService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增定时管理")
    @ApiOperation("新增定时管理")
    @PreAuthorize("@el.check('admin','CollectTask:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CollectTask resources) {
        CollectTaskService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改定时管理")
    @ApiOperation("修改定时管理")
    @PreAuthorize("@el.check('admin','CollectTask:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CollectTask resources) {
        CollectTaskService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}