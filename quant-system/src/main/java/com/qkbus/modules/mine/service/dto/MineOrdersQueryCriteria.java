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
public class MineOrdersQueryCriteria {
    @Query
    @ApiModelProperty(value = "购买的用户ID")
    private Long userId;
    @Query
    @ApiModelProperty(value = "矿机名称")
    private String mineName;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
