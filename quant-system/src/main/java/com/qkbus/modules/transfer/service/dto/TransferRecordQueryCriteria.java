
package com.qkbus.modules.transfer.service.dto;

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
public class TransferRecordQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "转出人")
    private Long fromUserId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "接受人")
    private Long toUserId;
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "地址")
    private String address;
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
