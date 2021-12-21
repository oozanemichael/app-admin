package com.qkbus.modules.mine.domain;

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
@TableName("mine_orders")
public class MineOrders extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "购买的用户ID")
    private Long userId;
    @ApiModelProperty(value = "矿机ID")
    private Long mineId;
    @ApiModelProperty(value = "矿机名称")
    private String mineName;
    @ApiModelProperty(value = "矿机图片")
    private String mineImage;
    @ApiModelProperty(value = "等级")
    private Integer level;
    @ApiModelProperty(value = "矿机价格")
    private BigDecimal price;
    @ApiModelProperty(value = "折合usdt金额 总金额")
    private BigDecimal usdtPrice;
    @ApiModelProperty(value = "利率")
    private BigDecimal rate;
    @ApiModelProperty(value = "剩余释放量")
    private BigDecimal fissionAmount;
    @ApiModelProperty(value = "复投金额")
    private BigDecimal pluralAmount;
    @ApiModelProperty(value = "复投金额")
    private BigDecimal pluralUsdtAmount;
    @ApiModelProperty(value = "已释放总数")
    private BigDecimal releaseAmount;
    @ApiModelProperty(value = "今天释放金额")
    private BigDecimal nowReleaseAmount;
    @ApiModelProperty(value = "释放币种")
    private String releaseCurrency;
    @ApiModelProperty(value = "释放次数")
    private Integer releaseNum;
    @ApiModelProperty(value = "周期")
    private Integer period;
    @ApiModelProperty(value = "算力")
    private Float hashrate;
    @ApiModelProperty(value = "矿机类型   0是正常  1是体验")
    private Integer mineType;
    @ApiModelProperty(value = "是否到期")
    private Integer enabled;
    @ApiModelProperty(value = "结束日期")
    private Timestamp endAt;
    @ApiModelProperty(value = "矿机类型   0是正常  1是体验")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp gmtCreate;
    private Timestamp gmtUpdated;
    private Integer version;

    public void copy(MineOrders source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
