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
public class PairTradeInfoDto extends BaseDto implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("日期")
    private Timestamp openDate;

    @ApiModelProperty("开盘价")
    private BigDecimal openPrice;

    @ApiModelProperty("收盘价")
    private BigDecimal closePrice;

    @ApiModelProperty("最低价")
    private BigDecimal lowPrice;

    @ApiModelProperty("最高价")
    private BigDecimal high;
}
