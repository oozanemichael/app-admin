package com.qkbus.modules.monitor.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.monitor.domain.Visits;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 少林一枝花
 * @date 2021-12-13
 */
public interface VisitsService extends BaseService<Visits> {

    /**
     * 提供给定时任务，每天0点执行
     */
    void save();

    /**
     * 新增记录
     *
     * @param request /
     */
    @Async
    void count(HttpServletRequest request);

    /**
     * 获取数据
     *
     * @return /
     */
    Object get();

    /**
     * getChartData
     *
     * @return /
     */
    Object getChartData();
}
