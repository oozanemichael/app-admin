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
@TableName("pair_order_entrust")
public class PairOrderEntrust  implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "交易对")
    private String currencyPair;
    @ApiModelProperty(value = "委托订单号")
    private String orderId;
    @ApiModelProperty(value = "0: 买 1：卖")
    private String orderType;
    @ApiModelProperty(value = "挂单金额")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "挂单数量")
    private BigDecimal orderNum;
    @ApiModelProperty(value = "未撮合数量")
    private BigDecimal balanceNum;
    @ApiModelProperty(value = "挂单时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp gmtCreate;
    @ApiModelProperty(value = "挂单人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;
    @ApiModelProperty(value = "未撮合的手续费")
    private BigDecimal balanceFee;

    public void copy(PairOrderEntrust source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
