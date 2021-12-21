
package com.qkbus.modules.recharge.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Data
public class RechargeQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "UID")
    private Long memberId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "公链")
    private String chainType;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "充值类型")
    private Integer rechargeType;

    @Query
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * BETWEEN
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
