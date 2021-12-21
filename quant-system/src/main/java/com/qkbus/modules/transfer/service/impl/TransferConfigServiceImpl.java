
package com.qkbus.modules.transfer.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.transfer.domain.TransferConfig;
import com.qkbus.modules.transfer.service.TransferConfigService;
import com.qkbus.modules.transfer.service.dto.TransferConfigDto;
import com.qkbus.modules.transfer.service.dto.TransferConfigQueryCriteria;
import com.qkbus.modules.transfer.service.mapper.TransferConfigMapper;
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
public class TransferConfigServiceImpl extends BaseServiceImpl<TransferConfigMapper, TransferConfig> implements TransferConfigService {

    private final IGenerator generator;

    @Override

    public Map<String, Object> queryAll(TransferConfigQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<TransferConfig> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), TransferConfigDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<TransferConfig> queryAll(TransferConfigQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(TransferConfig.class, criteria));
    }


    @Override
    public void download(List<TransferConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TransferConfigDto TransferConfig : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("币种", TransferConfig.getCurrency());
            map.put("名称", TransferConfig.getCurrencyName());
            map.put("手续费", TransferConfig.getFee());
            map.put("手续费币种", TransferConfig.getFeeCurrency());
            map.put("手续费是否百分比  0是  1不是", TransferConfig.getIsPercentage());
            map.put("状态", TransferConfig.getStatus());
            map.put("最小划转量", TransferConfig.getMinAmount());
            map.put("最大划转量", TransferConfig.getMaxAmount());
            map.put("每天划转次数", TransferConfig.getLimitNumDay());
            map.put("开始时间", TransferConfig.getStartDate());
            map.put("结束时间", TransferConfig.getEndDate());
            map.put("规则", TransferConfig.getRemark());
            map.put("创建时间", TransferConfig.getGmtCreate());
            map.put("修改时间", TransferConfig.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void create(TransferConfig resources) {
        this.save(resources);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void update(TransferConfig resources) {
        this.updateById(resources);
    }

    @Override

    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
