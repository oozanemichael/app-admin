
package com.qkbus.modules.assets.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
public class MemberWalletLogQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "UID")
    private Long uid;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "币种")
    private String currency;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "0 充值 1 提现 2 互转 3 推荐奖励 4 购买会员 5是购买信誉金套餐")
    private Integer logType;
}
