package com.qkbus.modules.transfer.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@TableName("transfer_config")
public class TransferConfig extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "名称")
    private String currencyName;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "手续费币种")
    private String feeCurrency;

    @ApiModelProperty(value = "手续费是否百分比  0是  1不是")
    private Integer isPercentage;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "最小划转量")
    private BigDecimal minAmount;

    @ApiModelProperty(value = "最大划转量")
    private BigDecimal maxAmount;

    @ApiModelProperty(value = "每天划转次数")
    private Integer limitNumDay;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "规则")
    private String remark;


    public void copy(TransferConfig source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
