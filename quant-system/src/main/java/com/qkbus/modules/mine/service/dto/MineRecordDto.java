package com.qkbus.modules.mine.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MineRecordDto extends BaseDto implements Serializable {

    private Integer id;

    @ApiModelProperty("会员id")
    private Long userId;

    @ApiModelProperty("推荐人id")
    private Long fromUserId;

    @ApiModelProperty("自己变化的原因")
    private String userChangeId;

    @ApiModelProperty("对方变化的原因")
    private String fromUserChangeId;

    @ApiModelProperty("金额")
    private BigDecimal count;

    @ApiModelProperty("开始金额")
    private BigDecimal startCount;

    @ApiModelProperty("结束金额")
    private BigDecimal endCount;

    @ApiModelProperty("币种类型")
    private String currency;

    @ApiModelProperty("平台释放个数")
    private BigDecimal releasePlatform;

    @ApiModelProperty("价格")
    private BigDecimal closeRate;

    @ApiModelProperty("利率")
    private BigDecimal rate;

    @ApiModelProperty("描述")
    private String comment;

    @ApiModelProperty("类型")
    private Integer recordType;

    @ApiModelProperty("其他")
    private Integer partnerNum;

    private Integer teamStatus;


}
