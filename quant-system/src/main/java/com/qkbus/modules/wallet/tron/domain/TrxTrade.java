package com.qkbus.modules.wallet.tron.domain;

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
 * @date 2021-05-15
 */
@Data
@TableName("wallet_trx_trade")
public class TrxTrade extends BaseEntity implements Serializable {


    /**
     * 已到账
     */
    public final static int TRX_TYPE_ARRIVAL = 0;
    /**
     * 已入账
     */
    public final static int TRX_TYPE_ENTERED = 1;

    /**
     * 已归集
     */
    public final static int TRX_TYPE_COLLECTED = 2;
    /**
     * 拒绝入账
     */
    public final static int TRX_TYPE_REJECT = -1;

    @TableId
    private Long id;

    @ApiModelProperty(value = "UID")
    private Long userId;

    @ApiModelProperty(value = "区块ID")
    private String blockId;

    @ApiModelProperty(value = "区块号")
    private Long blockNumber;

    @ApiModelProperty(value = "签名")
    private String signature;

    @ApiModelProperty(value = "交易ID")
    private String txId;

    @ApiModelProperty(value = "转入地址")
    private String fromAddr;

    @ApiModelProperty(value = "数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "hex数量")
    private String hexAmount;

    @ApiModelProperty(value = "接受地址")
    private String toAddr;

    @ApiModelProperty(value = "状态 0 未入账 1 已入账")
    private Integer status;

    @ApiModelProperty(value = "归集ID")
    private String collectId;

    @ApiModelProperty(value = "描述")
    private String commont;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "合约地址")
    private String contractAddress;

    @ApiModelProperty(value = "ABI合约")
    private String abi;

    @ApiModelProperty(value = "审核人")
    private String adminName;

    @ApiModelProperty(value = "充值时间")
    private String rechargeAt;


    public void copy(TrxTrade source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
