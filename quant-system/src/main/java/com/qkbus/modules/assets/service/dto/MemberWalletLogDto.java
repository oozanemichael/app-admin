
package com.qkbus.modules.assets.service.dto;

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
 * @date 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberWalletLogDto extends BaseDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("UID")
    private Long uid;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("金额")
    private BigDecimal amount;
    @ApiModelProperty("出账前金额")
    private BigDecimal beforeAmount;
    @ApiModelProperty("出账后金额")
    private BigDecimal afterAmount;
    @ApiModelProperty("0 充值 1 提现 2 互转 3 推荐奖励 4 购买会员 5是购买信誉金套餐")
    private Integer logType;
    @ApiModelProperty("备注")
    private String comment;
}
