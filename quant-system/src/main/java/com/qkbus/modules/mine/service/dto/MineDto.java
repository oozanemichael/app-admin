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
public class MineDto extends BaseDto implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("开始价格")
    private BigDecimal startPrice;

    @ApiModelProperty("结束价格")
    private BigDecimal endPrice;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("释放币种")
    private String releaseCurrency;

    @ApiModelProperty("已经卖出")
    private Integer alreadyNum;

    @ApiModelProperty("总数量")
    private Integer totalNum;

    @ApiModelProperty("算力")
    private Float hashrate;

    @ApiModelProperty("周期")
    private Integer period;

    @ApiModelProperty("倍率")
    private BigDecimal rate;

    @ApiModelProperty("0是开启  1是关闭  -1是不显示")
    private Integer status;

    @ApiModelProperty("是否是体验")
    private Integer type;


    @ApiModelProperty("0否1是")
    private Integer isDeleted;

    private Integer version;
}
