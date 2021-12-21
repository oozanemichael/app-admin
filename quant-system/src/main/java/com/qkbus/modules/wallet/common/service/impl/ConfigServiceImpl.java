
package com.qkbus.modules.wallet.common.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.wallet.common.domain.Config;
import com.qkbus.modules.wallet.common.service.ConfigService;
import com.qkbus.modules.wallet.common.service.dto.ConfigDto;
import com.qkbus.modules.wallet.common.service.dto.ConfigQueryCriteria;
import com.qkbus.modules.wallet.common.service.mapper.ConfigMapper;
import com.qkbus.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, Config> implements ConfigService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(ConfigQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Config> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), ConfigDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<Config> queryAll(ConfigQueryCriteria criteria) {
        criteria.setStatus(0);
        return this.list(QueryHelpPlus.getPredicate(Config.class, criteria));
    }


    @Override
    public void download(List<ConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ConfigDto Config : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("公链", Config.getChain());
            map.put("配置名", Config.getConfigName());
            map.put("配置值", Config.getConfigValue());
            map.put("配置描述", Config.getConfigRemark());
            map.put("状态", Config.getStatus());
            map.put("创建时间", Config.getGmtCreate());
            map.put("最新更新时间", Config.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Config resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Config resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }


}
