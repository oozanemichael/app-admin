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
public class PairOrderDealDto extends BaseDto implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("订单类型")
    private String orderType;

    @ApiModelProperty("交易对")
    private String currencyPair;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("购买的委托单号")
    private String buyOrderNo;

    @ApiModelProperty("出售的委托单号")
    private String sellOrderNo;

    @ApiModelProperty("购买人")
    private Long buyUser;

    @ApiModelProperty("出售人")
    private Long sellUser;

    @ApiModelProperty("撮合价格")
    private BigDecimal orderPrice;

    @ApiModelProperty("撮合数量")
    private BigDecimal orderNum;

    @ApiModelProperty("卖单手续费")
    private BigDecimal sellFee;

    @ApiModelProperty("买单手续费")
    private BigDecimal buyFee;

    @ApiModelProperty("是否已经加入锁仓释放记录，0：未加入 1：已加入")
    private Integer lockReleaseFlag;

    @ApiModelProperty("手续费分红记录 0:待分红 1:已分红 2:不分红")
    private Integer feeProfitFlag;
}
