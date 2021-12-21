
package com.qkbus.modules.wallet.tron.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
@Data
public class TrxAddressQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "UID")
    private Long userId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "公链 ")
    private String currency;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "地址")
    private String addr;
}
