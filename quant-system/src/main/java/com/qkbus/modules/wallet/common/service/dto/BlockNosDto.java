
package com.qkbus.modules.wallet.common.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlockNosDto extends BaseDto implements Serializable {
    private Long id;
    @ApiModelProperty("区块链名BTC,ETH...")
    private String currency;
    @ApiModelProperty("最新查询的区块数")
    private Integer chainNo;
    @ApiModelProperty("获取当前最新区块号")
    private Long newestBlockNo;
    @ApiModelProperty("当前同步状态")
    private String syncStatus;
    @ApiModelProperty("当前归集状态")
    private String gatherStatus;

}
