
package com.qkbus.modules.withdraw.service.dto;

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
public class WithdrawDto extends BaseDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("提币用户ID")
    private Long uid;
    @ApiModelProperty("公链")
    private String chainType;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("申请数量")
    private BigDecimal amount;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("手续费币种")
    private String feeCurrency;
    @ApiModelProperty("实际提现数量")
    private BigDecimal realAmount;
    @ApiModelProperty("提币地址")
    private String withdrawAddress;
    @ApiModelProperty("操作人")
    private String adminName;
    @ApiModelProperty("0待处理 1已完成 2已取消")
    private Integer status;
    @ApiModelProperty("处理说明")
    private String remark;
    @ApiModelProperty("转账HASH")
    private String motionHash;

}
