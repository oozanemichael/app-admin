
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
public class MemberWalletDto extends BaseDto implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @ApiModelProperty("UID")
    private Long uid;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("余额")
    private BigDecimal balance;
    @ApiModelProperty("冻结资产")
    private BigDecimal frozenBalance;
    @ApiModelProperty("乐观锁")
    private Integer version;

}
