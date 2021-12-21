
package com.qkbus.modules.wallet.common.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
public class CollectTaskQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Query
    @ApiModelProperty(value = "公链")
    private Integer chain;

    @Query
    @ApiModelProperty(value = "任务类型")
    private Integer taskType;
}
