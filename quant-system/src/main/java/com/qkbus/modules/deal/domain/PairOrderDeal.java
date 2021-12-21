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
@TableName("pair_order_deal")
public class PairOrderDeal  implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    @ApiModelProperty(value = "交易对")
    private String currencyPair;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "购买的委托单号")
    private String buyOrderNo;
    @ApiModelProperty(value = "出售的委托单号")
    private String sellOrderNo;
    @ApiModelProperty(value = "购买人")
    private Long buyUser;
    @ApiModelProperty(value = "出售人")
    private Long sellUser;
    @ApiModelProperty(value = "撮合价格")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "撮合数量")
    private BigDecimal orderNum;
    @ApiModelProperty(value = "撮合时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp gmtCreate;
    @ApiModelProperty(value = "卖单手续费")
    private BigDecimal sellFee;
    @ApiModelProperty(value = "买单手续费")
    private BigDecimal buyFee;
    @ApiModelProperty(value = "是否已经加入锁仓释放记录，0：未加入 1：已加入")
    private Integer lockReleaseFlag;
    @ApiModelProperty(value = "手续费分红记录 0:待分红 1:已分红 2:不分红")
    private Integer feeProfitFlag;

    public void copy(PairOrderDeal source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
