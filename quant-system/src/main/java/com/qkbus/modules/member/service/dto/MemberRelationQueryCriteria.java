
package com.qkbus.modules.member.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Data
public class MemberRelationQueryCriteria {

    @ApiModelProperty(value = "会员UID")
    @Query
    private Long memberId;
    @Query
    @ApiModelProperty(value = "父类ID")
    private Long parentId;

    @ApiModelProperty(value = "第几代")
    private Integer generation;

}
