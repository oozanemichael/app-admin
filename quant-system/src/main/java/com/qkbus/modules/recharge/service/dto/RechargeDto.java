
package com.qkbus.modules.recharge.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RechargeDto extends BaseDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("UID")
    private Long memberId;
    @ApiModelProperty("公链")
    private String chainType;
    @ApiModelProperty("充值类型 0 自动充值 1 手动充值")
    private Integer rechargeType;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("充币数量")
    private BigDecimal amount;
    @ApiModelProperty("充币地址")
    private String address;
    @ApiModelProperty("状态 0 待入账 1 已入账")
    private Integer status;

}
