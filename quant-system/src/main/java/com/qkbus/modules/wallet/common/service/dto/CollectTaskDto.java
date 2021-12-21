
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
public class CollectTaskDto extends BaseDto implements Serializable {
    private Long id;
    @ApiModelProperty("触发了哪条链归集 ETH TRON BTC LTC")
    private Integer chain;
    @ApiModelProperty("任务类型 0 手动同步 1 手动归集")
    private Integer taskType;
    @ApiModelProperty("归集描述")
    private String remark;
    @ApiModelProperty("开始区块号")
    private Long beginNo;
    @ApiModelProperty("结束区块号")
    private Long endNo;
    @ApiModelProperty("状态，0 未执行 1 已执行")
    private Integer status;

}
