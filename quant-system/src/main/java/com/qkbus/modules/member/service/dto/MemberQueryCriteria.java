
package com.qkbus.modules.member.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Data
public class MemberQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "UID")
    private Long uid;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "用户等级")
    private Integer grade;
    @Query
    @ApiModelProperty(value = "账户")
    private String account;
    /**
     * BETWEEN
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;


    @ApiModelProperty("邮箱")
    private String email;


    @ApiModelProperty("手机号")
    private String phone;

    @Query
    @ApiModelProperty(value = "地址")
    private String address;
}
