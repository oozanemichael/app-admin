
package com.qkbus.modules.transfer.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TransferConfigDto extends BaseDto implements Serializable {
    private Long id;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("名称")
    private String currencyName;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("手续费币种")
    private String feeCurrency;
    @ApiModelProperty("手续费是否百分比  0是  1不是")
    private Integer isPercentage;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("最小划转量")
    private BigDecimal minAmount;
    @ApiModelProperty("最大划转量")
    private BigDecimal maxAmount;
    @ApiModelProperty("每天划转次数")
    private Integer limitNumDay;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("规则")
    private String remark;

}
