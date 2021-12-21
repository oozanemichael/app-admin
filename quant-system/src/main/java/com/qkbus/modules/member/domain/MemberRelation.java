package com.qkbus.modules.member.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Data
@TableName("member_relation")
public class MemberRelation implements Serializable {
    @TableId
    private Long id;

    @ApiModelProperty(value = "上级用户")
    private Long parentId;

    @ApiModelProperty(value = "会员ID")
    private Long memberId;

    @ApiModelProperty(value = "第几代")
    private Integer generation;

    @ApiModelProperty(value = "创建时间")
    private Timestamp gmtCreate;


    public void copy(MemberRelation source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
