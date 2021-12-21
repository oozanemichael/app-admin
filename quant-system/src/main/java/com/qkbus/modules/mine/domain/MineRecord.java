package com.qkbus.modules.mine.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
@TableName("mine_record")
public class MineRecord extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "会员id")
    private Long userId;
    @ApiModelProperty(value = "推荐人id")
    private Long fromUserId;
    @ApiModelProperty(value = "自己变化的原因")
    private String userChangeId;
    @ApiModelProperty(value = "对方变化的原因")
    private String fromUserChangeId;
    @ApiModelProperty(value = "金额")
    private BigDecimal count;
    @ApiModelProperty(value = "开始金额")
    private BigDecimal startCount;
    @ApiModelProperty(value = "结束金额")
    private BigDecimal endCount;
    @ApiModelProperty(value = "币种类型")
    private String currency;
    @ApiModelProperty(value = "平台释放个数")
    private BigDecimal releasePlatform;
    @ApiModelProperty(value = "价格")
    private BigDecimal closeRate;
    @ApiModelProperty(value = "利率")
    private BigDecimal rate;
    @ApiModelProperty(value = "描述")
    private String comment;
    @ApiModelProperty(value = "类型")
    private Integer recordType;
    @ApiModelProperty(value = "其他")
    private Integer partnerNum;
    private Integer teamStatus;


    public void copy(MineRecord source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
