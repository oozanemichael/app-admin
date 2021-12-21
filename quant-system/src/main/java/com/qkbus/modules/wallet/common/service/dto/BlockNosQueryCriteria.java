
package com.qkbus.modules.wallet.common.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
public class BlockNosQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "区块链名BTC,ETH...")
    private String currency;
}
