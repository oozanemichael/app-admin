package com.qkbus.modules.deal.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CurrencyPairDto extends BaseDto implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("展示名称")
    private String dspName;

    @ApiModelProperty("基础货币")
    private String baseCurrency;

    @ApiModelProperty("计价货币")
    private String quoteCurrency;

    @ApiModelProperty("交易对名称")
    private String keyName;

    @ApiModelProperty("买单手续费")
    private BigDecimal takerFee;

    @ApiModelProperty("卖单手续费")
    private BigDecimal makerFee;

    @ApiModelProperty("开始交易时间")
    private Timestamp openTime;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer currencyOrder;

    @ApiModelProperty("价格小数位")
    private Integer pricePrecision;

    @ApiModelProperty("数量小数位")
    private Integer volumePrecision;

    @ApiModelProperty("限制买的上限")
    private Long buyUpperLimit;

    @ApiModelProperty("开盘价")
    private BigDecimal openPrice;
    @ApiModelProperty("是否能买")
    private Integer canBuy;
    @ApiModelProperty("是否能卖")
    private Integer canSell;

    @ApiModelProperty("最小买出数量")
    private BigDecimal minBuyVolume;
    @ApiModelProperty("最小卖出数量")
    private BigDecimal minSellVolume;
}
