
package com.qkbus.modules.member.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.member.domain.Member;
import com.qkbus.modules.member.service.MemberService;
import com.qkbus.modules.member.service.dto.MemberDto;
import com.qkbus.modules.member.service.dto.MemberQueryCriteria;
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
 * @date 2021-05-06
 */
@AllArgsConstructor
@Api(tags = "会员管理管理")
@RestController
@RequestMapping("/api/Member")
public class MemberController {

    private final MemberService MemberService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','Member:list')")
    public void download(HttpServletResponse response, MemberQueryCriteria criteria) throws IOException {
        MemberService.download(generator.convert(MemberService.queryAll(criteria), MemberDto.class), response);
    }

    @GetMapping
    @Log("查询会员管理")
    @ApiOperation("查询会员管理")
    @PreAuthorize("@el.check('admin','Member:list')")
    public ResponseEntity<Object> getMembers(MemberQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(MemberService.queryAll(criteria, pageable), HttpStatus.OK);
    }


    @PutMapping
    @Log("修改会员管理")
    @ApiOperation("修改会员管理")
    @PreAuthorize("@el.check('admin','Member:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Member resources) {
        MemberService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/statistical")
    @Log("统计会员信息")
    @ApiOperation("统计会员信息")
    public ResponseEntity<Object> statisticalMember() {
        return new ResponseEntity<>(MemberService.statisticalMember(), HttpStatus.OK);
    }

    @GetMapping("/statisticalGrade")
    @Log("统计会员等级信息")
    @ApiOperation("统计会员等级信息")
    public ResponseEntity<Object> statisticalMemberGrade() {
        return new ResponseEntity<>(MemberService.statisticalMemberGrade(), HttpStatus.OK);
    }


}