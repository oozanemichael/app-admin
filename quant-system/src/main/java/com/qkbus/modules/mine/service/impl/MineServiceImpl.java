package com.qkbus.modules.mine.service.impl;

import com.qkbus.modules.mine.domain.Mine;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.mine.service.MineService;
import com.qkbus.modules.mine.service.dto.MineDto;
import com.qkbus.modules.mine.service.dto.MineQueryCriteria;
import com.qkbus.modules.mine.service.mapper.MineMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author 少林一枝花
 * @date 2021-07-03
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MineServiceImpl extends BaseServiceImpl<MineMapper, Mine> implements MineService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(MineQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Mine> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MineDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<Mine> queryAll(MineQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(Mine.class, criteria));
    }


    @Override
    public void download(List<MineDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MineDto mine : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("名称", mine.getName());
            map.put("图片", mine.getImage());
            map.put("等级", mine.getLevel());
            map.put("开始价格", mine.getStartPrice());
            map.put("结束价格", mine.getEndPrice());
            map.put("币种", mine.getCurrency());
            map.put("释放币种", mine.getReleaseCurrency());
            map.put("已经卖出", mine.getAlreadyNum());
            map.put("总数量", mine.getTotalNum());
            map.put("算力", mine.getHashrate());
            map.put("周期", mine.getPeriod());
            map.put("倍率", mine.getRate());
            map.put("0是开启  1是关闭  -1是不显示", mine.getStatus());
            map.put("是否是体验", mine.getType());
            map.put("创建时间", mine.getGmtCreate());
            map.put("修改时间", mine.getGmtUpdated());
            map.put("0否1是", mine.getIsDeleted());
            map.put(" version", mine.getVersion());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Mine resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Mine resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Integer> ids) {
        this.removeByIds(ids);
    }
}
