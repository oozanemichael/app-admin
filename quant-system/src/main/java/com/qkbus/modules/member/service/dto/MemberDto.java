
package com.qkbus.modules.member.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberDto extends BaseDto implements Serializable {


    @ApiModelProperty("自增ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("用户ID")
    private Long uid;
    @ApiModelProperty("会员账号")
    private String account;
    @ApiModelProperty("会员昵称")
    private String nickName;
    @ApiModelProperty("绑定邮箱")
    private String email;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("会员头像")
    private String avatar;
    @ApiModelProperty("邀请码")
    private String inviteCode;
    @ApiModelProperty("个人签名")
    private String personSign;
    @ApiModelProperty("会员状态")
    private Integer status;
    @ApiModelProperty("6位数的盐值")
    private String salt;
    @ApiModelProperty("直推人数")
    private Integer inviteCount;
    @ApiModelProperty("团队人数")
    private Integer teamCount;
    @ApiModelProperty("有效直推人数")
    private Integer validInviteCount;
    @ApiModelProperty("有效团队人数")
    private Integer validTeamCount;
    @ApiModelProperty("设置等级")
    private Integer adminGrade;
    @ApiModelProperty("用户等级")
    private Integer grade;

    @ApiModelProperty("是否有效")
    private Integer validStatus;
    @ApiModelProperty("试用期")
    private Integer isTrial;

    @ApiModelProperty("注册人数")
    private Integer regCount;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态")
    private Integer idEntityStatus;
}
