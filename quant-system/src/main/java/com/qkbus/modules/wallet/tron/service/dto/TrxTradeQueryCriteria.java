
package com.qkbus.modules.wallet.tron.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
@Data
public class TrxTradeQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "UID")
    private String userId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "交易ID")
    private String txId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "转入地址")
    private String fromAddr;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "接受地址")
    private String toAddr;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "状态 0 未入账 1 已入账")
    private Integer status;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "归集ID")
    private String collectId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "币种")
    private String currency;
}
