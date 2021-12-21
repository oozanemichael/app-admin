
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
public class ConfigDto extends BaseDto implements Serializable {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty(value = "公链")
    private Integer chain;
    @ApiModelProperty("配置名")
    private String configName;
    @ApiModelProperty("配置值")
    private String configValue;
    @ApiModelProperty("配置描述")
    private String configRemark;
    @ApiModelProperty("状态")
    private Integer status;

}
