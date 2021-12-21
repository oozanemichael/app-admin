
package com.qkbus.modules.member.rest;

import com.qkbus.dozer.service.IGenerator;
import com.qkbus.logging.aop.log.Log;
import com.qkbus.modules.member.domain.MemberRanking;
import com.qkbus.modules.member.service.MemberRankingService;
import com.qkbus.modules.member.service.dto.MemberRankingDto;
import com.qkbus.modules.member.service.dto.MemberRankingQueryCriteria;
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
@Api(tags = "会员排行管理")
@RestController
@RequestMapping("/api/MemberRanking")
public class MemberRankingController {

    private final MemberRankingService MemberRankingService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','MemberRanking:list')")
    public void download(HttpServletResponse response, MemberRankingQueryCriteria criteria) throws IOException {
        MemberRankingService.download(generator.convert(MemberRankingService.queryAll(criteria), MemberRankingDto.class), response);
    }

    @GetMapping
    @Log("查询会员排行")
    @ApiOperation("查询会员排行")
    @PreAuthorize("@el.check('admin','MemberRanking:list')")
    public ResponseEntity<Object> getMemberRankings(MemberRankingQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(MemberRankingService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员排行")
    @ApiOperation("新增会员排行")
    @PreAuthorize("@el.check('admin','MemberRanking:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MemberRanking resources) {
        MemberRankingService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改会员排行")
    @ApiOperation("修改会员排行")
    @PreAuthorize("@el.check('admin','MemberRanking:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MemberRanking resources) {
        MemberRankingService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员排行")
    @ApiOperation("删除会员排行")
    @PreAuthorize("@el.check('admin','MemberRanking:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Set<Integer> ids) {
        MemberRankingService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}