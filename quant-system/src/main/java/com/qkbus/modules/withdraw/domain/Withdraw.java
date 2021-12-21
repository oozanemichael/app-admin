package com.qkbus.modules.withdraw.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@TableName("withdraw")
public class Withdraw extends BaseEntity implements Serializable {


    //审核
    public final static int AUDIT = 0;
    //提币拒绝
    public final static int EXTRACT_REFUSE = -1;
    //提币审核通过
    public final static int EXTRACT_PASS = 1;
    //转帐中
    public final static int IN_THE_TRANSFER = 2;
    @TableId
    private Long id;

    @ApiModelProperty(value = "提币用户ID")
    private Long uid;

    @ApiModelProperty(value = "公链")
    private String chainType;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "申请数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "手续费币种")
    private String feeCurrency;

    @ApiModelProperty(value = "实际提现数量")
    private BigDecimal realAmount;

    @ApiModelProperty(value = "提币地址")
    @NotBlank
    private String withdrawAddress;

    @ApiModelProperty(value = "操作人")
    private String adminName;

    @ApiModelProperty(value = "0待处理 1已完成 2已取消")
    private Integer status;

    @ApiModelProperty(value = "处理说明")
    private String remark;

    @ApiModelProperty(value = "转账HASH")
    private String motionHash;


    public void copy(Withdraw source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
