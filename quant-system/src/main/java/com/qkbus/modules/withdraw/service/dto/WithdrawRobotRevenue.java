package com.qkbus.modules.withdraw.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRobotRevenue {

    /*
     * 用户uid
     * */
    private Long uid;

    /*
     *币种
     * */
    private String currency;

    /*
     * 金额
     * */
    private BigDecimal amount;


}
