
package com.qkbus.modules.wallet.tron.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TrxTradeDto extends BaseDto implements Serializable {
    private Long id;
    @ApiModelProperty("UID")
    private String userId;
    @ApiModelProperty("区块ID")
    private String blockId;
    @ApiModelProperty("区块号")
    private Long blockNumber;
    @ApiModelProperty("签名")
    private String signature;
    @ApiModelProperty("交易ID")
    private String txId;
    @ApiModelProperty("转入地址")
    private String fromAddr;
    @ApiModelProperty("数量")
    private BigDecimal amount;
    @ApiModelProperty("hex数量")
    private String hexAmount;
    @ApiModelProperty("接受地址")
    private String toAddr;
    @ApiModelProperty("状态 0 未入账 1 已入账")
    private Integer status;
    @ApiModelProperty("归集ID")
    private String collectId;
    @ApiModelProperty("描述")
    private String commont;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("ABI合约")
    private String abi;
    @ApiModelProperty("审核人")
    private String adminName;
    @ApiModelProperty("充值时间")
    private String rechargeAt;

}
