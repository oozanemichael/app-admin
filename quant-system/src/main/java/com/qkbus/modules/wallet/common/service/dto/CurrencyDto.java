
package com.qkbus.modules.wallet.common.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
public class CurrencyDto implements Serializable {
    private Integer id;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("货币名称")
    private String currencyName;
    @ApiModelProperty("最小充值额")
    private BigDecimal minRecharge;
    @ApiModelProperty("0 BTC 1 ETH 2 TRX")
    private Integer chain;
    @ApiModelProperty("是否可充值")
    private Integer canRecharge;
    @ApiModelProperty("合约")
    private String tokenAddr;
    @ApiModelProperty("代币精度")
    private String currencyDecimal;
}
