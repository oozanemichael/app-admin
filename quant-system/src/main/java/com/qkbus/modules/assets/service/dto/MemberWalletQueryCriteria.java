
package com.qkbus.modules.assets.service.dto;

import com.qkbus.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
public class MemberWalletQueryCriteria {
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
     * BETWEEN
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
