
package com.qkbus.modules.member.service.dto;

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
public class MemberRankingQueryCriteria {
    /**
     * 精确
     */
    @Query
    @ApiModelProperty(value = "uid")
    private Integer uid;
    /**
     * BETWEEN
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;
}
