package com.qkbus.modules.deal.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import com.qkbus.annotation.Query;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
public class PairOrderEntrustQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String currencyPair;

    @Query(type = Query.Type.INNER_LIKE)
    private String orderId;
    @Query
    @ApiModelProperty(value = "0: 买 1：卖")
    private String orderType;
    @Query
    @ApiModelProperty(value = "订单状态")
    private String status;
}
