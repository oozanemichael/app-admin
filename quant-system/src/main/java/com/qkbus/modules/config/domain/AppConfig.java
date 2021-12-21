package com.qkbus.modules.config.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-06-10
 */
@Data
@TableName("app_config")
public class AppConfig extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "配置名")
    private String configName;
    @ApiModelProperty(value = "配置值")
    private String configValue;
    @ApiModelProperty(value = "配置描述")
    private String configRemark;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

    public void copy(AppConfig source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
