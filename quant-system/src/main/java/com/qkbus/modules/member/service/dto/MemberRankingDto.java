
package com.qkbus.modules.member.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberRankingDto extends BaseDto implements Serializable {
    @ApiModelProperty("自增 ID")
    private Integer id;
    @ApiModelProperty("uid")
    private Integer uid;
    @ApiModelProperty("收益")
    private String revenue;
    @ApiModelProperty("类型")
    private Integer type;

}
