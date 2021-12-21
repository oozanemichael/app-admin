package com.qkbus.modules.wallet.tron.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
@Data
@TableName("wallet_trx_address")
public class TrxAddress extends BaseEntity implements Serializable {
    @TableId
    private Long id;

    @ApiModelProperty(value = "UID")
    private Long userId;

    @ApiModelProperty(value = "公链 ")
    private String currency;

    @ApiModelProperty(value = "地址密码")
    private String password;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "状态")
    private Integer status;


    public void copy(TrxAddress source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
