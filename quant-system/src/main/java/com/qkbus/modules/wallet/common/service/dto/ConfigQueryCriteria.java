
package com.qkbus.modules.wallet.common.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
public class ConfigQueryCriteria {


    @Query
    @ApiModelProperty(value = "公链")
    private Integer chain;
    @Query
    @ApiModelProperty(value = "配置名")
    private String configName;
    @Query
    @ApiModelProperty(value = "配置值")
    private String configValue;
    @Query
    @ApiModelProperty(value = "配置描述")
    private String configRemark;
    @Query
    @ApiModelProperty(value = "状态")
    private Integer status;

}
