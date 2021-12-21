
package com.qkbus.modules.withdraw.service.dto;

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
public class WithdrawQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "提币用户ID")
    private Long uid;
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
    @ApiModelProperty(value = "币种")
    private String currency;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "0待处理 1已完成 2已取消")
    private Integer status;

    /**
     * BETWEEN
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
