package com.qkbus.modules.deal.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

import com.qkbus.annotation.Query;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
public class PairOrderDealQueryCriteria {
    @Query
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @Query(type = Query.Type.INNER_LIKE)
    private String currencyPair;
    @Query
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @Query
    @ApiModelProperty(value = "购买的委托单号")
    private String buyOrderNo;
    @Query
    @ApiModelProperty(value = "出售的委托单号")
    private String sellOrderNo;
    @Query
    @ApiModelProperty(value = "购买人")
    private Long buyUser;
    @Query
    @ApiModelProperty(value = "出售人")
    private Long sellUser;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
