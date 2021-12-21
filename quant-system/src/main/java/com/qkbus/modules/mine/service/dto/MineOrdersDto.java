package com.qkbus.modules.mine.service.dto;

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
public class MineOrdersDto extends BaseDto implements Serializable {

    private Long id;

    @ApiModelProperty("购买的用户ID")
    private Long userId;

    @ApiModelProperty("矿机ID")
    private Long mineId;

    @ApiModelProperty("矿机名称")
    private String mineName;

    @ApiModelProperty("矿机图片")
    private String mineImage;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("矿机价格")
    private BigDecimal price;

    @ApiModelProperty("折合usdt金额 总金额")
    private BigDecimal usdtPrice;

    @ApiModelProperty("利率")
    private BigDecimal rate;

    @ApiModelProperty("剩余释放量")
    private BigDecimal fissionAmount;

    @ApiModelProperty("复投金额")
    private BigDecimal pluralAmount;

    @ApiModelProperty("复投金额")
    private BigDecimal pluralUsdtAmount;

    @ApiModelProperty("已释放总数")
    private BigDecimal releaseAmount;

    @ApiModelProperty("今天释放金额")
    private BigDecimal nowReleaseAmount;

    @ApiModelProperty("释放币种")
    private String releaseCurrency;

    @ApiModelProperty("释放次数")
    private Integer releaseNum;

    @ApiModelProperty("周期")
    private Integer period;

    @ApiModelProperty("算力")
    private Float hashrate;

    @ApiModelProperty("矿机类型   0是正常  1是体验")
    private Integer mineType;

    @ApiModelProperty("是否到期")
    private Integer enabled;

    @ApiModelProperty("结束日期")
    private Timestamp endAt;


    private Integer version;
}
