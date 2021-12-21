package com.qkbus.modules.member.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("quant_member_ranking")
public class MemberRanking extends BaseEntity implements Serializable {
    @TableId
    @ApiModelProperty(value = "自增 ID")
    private Integer id;

    @ApiModelProperty(value = "uid")
    private Long uid;

    @ApiModelProperty(value = "收益")
    private String revenue;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

    public void copy(MemberRanking source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
