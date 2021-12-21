package com.qkbus.modules.deal.service.dto;

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
public class CurrencyPairQueryCriteria {

    @Query
    private String quoteCurrency;


    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> gmtCreate;


    @Query(type = Query.Type.INNER_LIKE)
    private String dspName;
}
