package com.qkbus.modules.mine.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

import com.qkbus.annotation.Query;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Data
public class MineRecordQueryCriteria {
    @Query
    @ApiModelProperty(value = "会员id")
    private Long userId;
    @Query
    @ApiModelProperty(value = "推荐人id")
    private Long fromUserId;
    @Query
    @ApiModelProperty(value = "类型")
    private Integer recordType;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
