
package com.qkbus.modules.withdraw.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.withdraw.domain.WithdrawConfig;
import com.qkbus.modules.withdraw.service.WithdrawConfigService;
import com.qkbus.modules.withdraw.service.dto.WithdrawConfigDto;
import com.qkbus.modules.withdraw.service.dto.WithdrawConfigQueryCriteria;
import com.qkbus.modules.withdraw.service.mapper.WithdrawConfigMapper;
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
public class WithdrawConfigServiceImpl extends BaseServiceImpl<WithdrawConfigMapper, WithdrawConfig> implements WithdrawConfigService {

    private final IGenerator generator;

    @Override

    public Map<String, Object> queryAll(WithdrawConfigQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<WithdrawConfig> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), WithdrawConfigDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<WithdrawConfig> queryAll(WithdrawConfigQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WithdrawConfig.class, criteria));
    }


    @Override
    public void download(List<WithdrawConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WithdrawConfigDto WithdrawConfig : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("币种", WithdrawConfig.getCurrency());
            map.put("币种名称", WithdrawConfig.getCurrencyName());
            map.put("手续费", WithdrawConfig.getFee());
            map.put("提币手续费", WithdrawConfig.getFeeCurrency());
            map.put("手续费是否百分比  0是  1不是", WithdrawConfig.getIsPercentage());
            map.put("状态", WithdrawConfig.getStatus());
            map.put("最小提币量", WithdrawConfig.getMinAmount());
            map.put("最大提币量", WithdrawConfig.getMaxAmount());
            map.put("每天提币次数", WithdrawConfig.getLimitNumDay());
            map.put("开始时间", WithdrawConfig.getStartDate());
            map.put("结束时间", WithdrawConfig.getEndDate());
            map.put("备注说明", WithdrawConfig.getRemark());
            map.put("创建时间", WithdrawConfig.getGmtCreate());
            map.put("修改时间", WithdrawConfig.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(WithdrawConfig resources) {
        this.save(resources);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void update(WithdrawConfig resources) {
        this.updateById(resources);
    }

    @Override

    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
