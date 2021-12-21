
package com.qkbus.modules.member.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Data
public class MemberRelationDto implements Serializable {

    @ApiModelProperty(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    @ApiModelProperty(value = "用户名")
    private Long uid;

    @ApiModelProperty(value = "账号")
    private String account;


    @ApiModelProperty(value = "子代团队人数")
    private Integer teamCount;

    @ApiModelProperty(value = "子代代数  最大的代数")
    private Integer maxGeneration;


    @ApiModelProperty(value = "子代直推人数")
    private Integer inviteCount;


    @ApiModelProperty(value = "第几代")
    private Integer generation;


    @ApiModelProperty(value = "用户等级")
    private Integer grade;


    @ApiModelProperty(value = "注册时件")
    private Date regDate;

}
