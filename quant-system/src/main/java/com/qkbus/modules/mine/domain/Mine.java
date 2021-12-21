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
@TableName("mine")
public class Mine extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "图片")
    private String image;
    @ApiModelProperty(value = "等级")
    private Integer level;
    @ApiModelProperty(value = "开始价格")
    private BigDecimal startPrice;
    @ApiModelProperty(value = "结束价格")
    private BigDecimal endPrice;
    @ApiModelProperty(value = "币种")
    private String currency;
    @ApiModelProperty(value = "释放币种")
    private String releaseCurrency;
    @ApiModelProperty(value = "已经卖出")
    private Integer alreadyNum;
    @ApiModelProperty(value = "总数量")
    private Integer totalNum;
    @ApiModelProperty(value = "算力")
    private Float hashrate;
    @ApiModelProperty(value = "周期")
    private Integer period;
    @ApiModelProperty(value = "倍率")
    private BigDecimal rate;
    @ApiModelProperty(value = "0是开启  1是关闭  -1是不显示")
    private Integer status;
    @ApiModelProperty(value = "是否是体验")
    private Integer type;
    @ApiModelProperty(value = "创建时间")
    private Timestamp gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Timestamp gmtUpdated;
    @TableLogic
    @ApiModelProperty(value = "0否1是")
    private Integer isDeleted;
    private Integer version;

    public void copy(Mine source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
