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
public class PairOrderEntrustDto extends BaseDto implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("交易对")
    private String currencyPair;

    @ApiModelProperty("委托订单号")
    private String orderId;

    @ApiModelProperty("0: 买 1：卖")
    private String orderType;

    @ApiModelProperty("挂单金额")
    private BigDecimal orderPrice;

    @ApiModelProperty("挂单数量")
    private BigDecimal orderNum;

    @ApiModelProperty("未撮合数量")
    private BigDecimal balanceNum;

    @ApiModelProperty("挂单人")
    private String createBy;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("手续费")
    private BigDecimal fee;

    @ApiModelProperty("未撮合的手续费")
    private BigDecimal balanceFee;
}
