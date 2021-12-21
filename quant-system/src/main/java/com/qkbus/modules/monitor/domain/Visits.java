package com.qkbus.modules.monitor.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * pv 与 ip 统计
 *
 * @author 少林一枝花
 * @date 2021-12-13
 */
@Data
@TableName("sys_visits")
public class Visits implements Serializable {

    @TableId
    private Long id;

    private String date;

    private Long pvCounts;

    private Long ipCounts;

    @TableField(fill = FieldFill.INSERT)
    private Timestamp gmtCreate;

    private String weekDay;
}
