package com.qkbus.modules.config.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import com.qkbus.annotation.Query;

/**
 * @author 少林一枝花
 * @date 2021-06-10
 */
@Data
public class AppConfigQueryCriteria {
    @Query
    @ApiModelProperty(value = "配置名")
    private String configName;
}
