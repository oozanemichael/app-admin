
package com.qkbus.modules.transfer.service.dto;

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
public class TransferRecordDto extends BaseDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("转出人")
    private Long fromUserId;
    @ApiModelProperty("接受人")
    private Long toUserId;
    @ApiModelProperty(value = "转出方地址")
    private String fromAddress;
    @ApiModelProperty(value = "接受方地址")
    private String toAddress;
    @ApiModelProperty("转出账号")
    private String fromAccount;
    @ApiModelProperty("转入账号")
    private String toAccount;
    @ApiModelProperty("数量")
    private BigDecimal amount;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("手续费币种")
    private String feeCurrency;
    @ApiModelProperty("手续费利率")
    private BigDecimal feeRate;

}
