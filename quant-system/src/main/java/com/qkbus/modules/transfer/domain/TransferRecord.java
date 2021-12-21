package com.qkbus.modules.transfer.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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
@TableName("transfer_record")
public class TransferRecord extends BaseEntity implements Serializable {
    @TableId
    private Long id;

    @ApiModelProperty(value = "转出人")
    private Long fromUserId;

    @ApiModelProperty(value = "接受人")
    private Long toUserId;

    @ApiModelProperty(value = "转出方地址")
    private String fromAddress;


    @ApiModelProperty(value = "接受方地址")
    private String toAddress;

    @ApiModelProperty(value = "转出账号")
    private String fromAccount;

    @ApiModelProperty(value = "转入账号")
    private String toAccount;

    @ApiModelProperty(value = "数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "手续费币种")
    private String feeCurrency;

    @ApiModelProperty(value = "手续费利率")
    private BigDecimal feeRate;


    public void copy(TransferRecord source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
