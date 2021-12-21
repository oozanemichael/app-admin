package com.qkbus.modules.wallet.common.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@TableName("wallet_collect_task")
public class CollectTask extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公链")
    private Integer chain;

    @ApiModelProperty(value = "任务类型 0 手动同步 1 手动归集")
    private Integer taskType;

    @ApiModelProperty(value = "归集描述")
    private String remark;

    @ApiModelProperty(value = "开始区块号")
    private Long beginNo;

    @ApiModelProperty(value = "结束区块号")
    private Long endNo;

    @ApiModelProperty(value = "状态，0 未执行 1 已执行")
    private Integer status;

    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
    public void copy(CollectTask source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
