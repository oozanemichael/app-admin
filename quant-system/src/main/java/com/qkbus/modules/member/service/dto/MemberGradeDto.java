package com.qkbus.modules.member.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "StatisticalUserVo", description = "统计用户信息Vo")
public class MemberGradeDto {

    @ApiModelProperty(value = "人数")
    private Integer gradeCount;

    @ApiModelProperty(value = "等级")
    private Integer grade;

    @ApiModelProperty(value = "等级名称")
    private String gradeName;

}
