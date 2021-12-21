package com.qkbus.modules.recharge.domain;

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
@TableName("recharge")
public class Recharge extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "UID")
    private Long memberId;

    @ApiModelProperty(value = "公链")
    private String chainType;

    @ApiModelProperty(value = "充值类型 0 自动充值 1 手动充值")
    private Integer rechargeType;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "充币数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "充币地址")
    private String address;

    @ApiModelProperty(value = "状态 0 待入账 1 已入账")
    private Integer status;


    public void copy(Recharge source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
