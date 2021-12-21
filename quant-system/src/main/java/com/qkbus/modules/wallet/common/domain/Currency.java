package com.qkbus.modules.wallet.common.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@TableName("wallet_currency")
public class Currency implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "币种")
    @NotBlank
    private String currency;

    @ApiModelProperty(value = "货币名称")
    @NotBlank
    private String currencyName;

    @ApiModelProperty(value = "最小充值额")
    @NotNull
    private BigDecimal minRecharge;

    @ApiModelProperty(value = "0 BTC 1 ETH 2 TRX")
    @NotNull
    private Integer chain;

    @ApiModelProperty(value = "是否可充值")
    private Integer canRecharge;

    @ApiModelProperty(value = "合约")
    private String tokenAddr;

    @ApiModelProperty(value = "代币精度")
    private String currencyDecimal;
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

    public void copy(Currency source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
