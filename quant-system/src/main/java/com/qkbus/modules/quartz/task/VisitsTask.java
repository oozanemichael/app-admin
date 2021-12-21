package com.qkbus.modules.quartz.task;

import com.qkbus.modules.monitor.service.VisitsService;
import org.springframework.stereotype.Component;

/**
 * @author 少林一枝花
 * @date 2021-12-25
 */
@Component
public class VisitsTask {

    private final VisitsService visitsService;

    public VisitsTask(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run() {
        visitsService.save();
    }
}
