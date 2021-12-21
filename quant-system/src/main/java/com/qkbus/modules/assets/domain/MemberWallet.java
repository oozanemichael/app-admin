package com.qkbus.modules.assets.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_asset")
public class MemberWallet extends BaseEntity implements Serializable {
    @TableId
    private Long id;

    @ApiModelProperty(value = "UID")
    private Long uid;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "冻结资产")
    private BigDecimal frozenBalance;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;


    public void copy(MemberWallet source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
