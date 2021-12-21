
package com.qkbus.modules.member.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.member.service.MemberRelationService;
import com.qkbus.modules.member.service.dto.MemberRelationDto;
import com.qkbus.modules.member.service.dto.MemberRelationQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@AllArgsConstructor
@Api(tags = "会员关系管理管理")
@RestController
@RequestMapping("/api/MemberRelation")
public class MemberRelationController {

    private final MemberRelationService MemberRelationService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','MemberRelation:list')")
    public void download(HttpServletResponse response, MemberRelationQueryCriteria criteria) throws IOException {
        MemberRelationService.download(generator.convert(MemberRelationService.queryAll(criteria), MemberRelationDto.class), response);
    }

    @GetMapping
    @Log("查询会员关系管理")
    @ApiOperation("查询会员关系管理")
    @PreAuthorize("@el.check('admin','MemberRelation:list')")
    public ResponseEntity<Object> getMemberRelations(MemberRelationQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(MemberRelationService.queryAll(criteria, pageable), HttpStatus.OK);
    }


}