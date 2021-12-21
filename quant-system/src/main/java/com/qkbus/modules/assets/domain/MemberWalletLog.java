package com.qkbus.modules.assets.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
@TableName("member_asset_log")
public class MemberWalletLog implements Serializable {
    @TableId
    private Long id;

    @ApiModelProperty(value = "UID")
    private Long uid;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "出账前金额")
    private BigDecimal beforeAmount;

    @ApiModelProperty(value = "出账后金额")
    private BigDecimal afterAmount;

    @ApiModelProperty(value = "0 充值 1 提现 2 互转 3 推荐奖励 4 购买会员 5是购买信誉金套餐")
    private Integer logType;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp gmtCreate;

    @ApiModelProperty(value = "备注")
    private String comment;


    public void copy(MemberWalletLog source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
