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
@TableName("currency_pair")
public class CurrencyPair  implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "展示名称")
    private String dspName;
    @ApiModelProperty(value = "基础货币")
    private String baseCurrency;
    @ApiModelProperty(value = "计价货币")
    private String quoteCurrency;
    @ApiModelProperty(value = "交易对名称")
    private String keyName;
    @ApiModelProperty(value = "买单手续费")
    private BigDecimal takerFee;
    @ApiModelProperty(value = "卖单手续费")
    private BigDecimal makerFee;
    @ApiModelProperty(value = "开始交易时间")
    private Timestamp openTime;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "排序")
    private Integer currencyOrder;
    @ApiModelProperty(value = "价格小数位")
    private Integer pricePrecision;
    @ApiModelProperty(value = "数量小数位")
    private Integer volumePrecision;
    @ApiModelProperty(value = "限制买的上限")
    private BigDecimal buyUpperLimit;
    @ApiModelProperty(value = "开盘价")
    private BigDecimal openPrice;
    @ApiModelProperty("是否能买")
    private Integer canBuy;
    @ApiModelProperty("是否能卖")
    private Integer canSell;

    @ApiModelProperty("最小买出数量")
    private BigDecimal minBuyVolume;
    @ApiModelProperty("最小卖出数量")
    private BigDecimal minSellVolume;

    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

    public void copy(CurrencyPair source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
