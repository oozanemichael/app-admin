package com.qkbus.modules.config.service.mapper;

import com.qkbus.common.mapper.CoreMapper;
import com.qkbus.modules.config.domain.AppConfig;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 少林一枝花
 * @date 2021-06-10
 */

public interface AppConfigMapper extends CoreMapper<AppConfig> {


    @Select("SELECT id, config_name, config_value, config_remark, status, gmt_create, gmt_updated FROM app_config WHERE status = 1")
    List<AppConfig> quantConfigs();

}
