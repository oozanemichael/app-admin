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
@TableName("wallet_block_nos")
public class BlockNos extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "区块链名BTC,ETH...")
    private String currency;

    @ApiModelProperty(value = "最新查询的区块数")
    private Integer chainNo;

    @ApiModelProperty(value = "获取当前最新区块号")
    private Long newestBlockNo;

    @ApiModelProperty(value = "当前同步状态")
    private String syncStatus;

    @ApiModelProperty(value = "当前归集状态")
    private String gatherStatus;

    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;
    public void copy(BlockNos source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
