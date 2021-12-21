package com.qkbus.modules.deal.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
@TableName("pair_trade_info")
public class PairTradeInfo  implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "日期")
    private Timestamp openDate;
    @ApiModelProperty(value = "开盘价")
    private BigDecimal openPrice;
    @ApiModelProperty(value = "收盘价")
    private BigDecimal closePrice;
    @ApiModelProperty(value = "最低价")
    private BigDecimal lowPrice;
    @ApiModelProperty(value = "最高价")
    private BigDecimal high;

    public void copy(PairTradeInfo source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
