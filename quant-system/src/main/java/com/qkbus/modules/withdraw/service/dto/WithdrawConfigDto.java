
package com.qkbus.modules.withdraw.service.dto;

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
public class WithdrawConfigDto extends BaseDto implements Serializable {
    private Long id;
    @ApiModelProperty("币种")
    private String currency;
    @ApiModelProperty("币种名称")
    private String currencyName;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("提币手续费")
    private String feeCurrency;
    @ApiModelProperty("手续费是否百分比  0是  1不是")
    private Integer isPercentage;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("最小提币量")
    private BigDecimal minAmount;
    @ApiModelProperty("最大提币量")
    private BigDecimal maxAmount;
    @ApiModelProperty("每天提币次数")
    private Integer limitNumDay;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("备注说明")
    private String remark;

}
