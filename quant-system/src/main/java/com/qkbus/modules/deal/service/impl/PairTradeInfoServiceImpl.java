package com.qkbus.modules.deal.service.impl;

import com.qkbus.modules.deal.domain.PairTradeInfo;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.deal.service.PairTradeInfoService;
import com.qkbus.modules.deal.service.dto.PairTradeInfoDto;
import com.qkbus.modules.deal.service.dto.PairTradeInfoQueryCriteria;
import com.qkbus.modules.deal.service.mapper.PairTradeInfoMapper;
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
public class PairTradeInfoServiceImpl extends BaseServiceImpl
        <PairTradeInfoMapper, PairTradeInfo> implements PairTradeInfoService {

    private final IGenerator generator;

    @Override
    public Map
            <String, Object> queryAll(PairTradeInfoQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<PairTradeInfo> page = new PageInfo<>(queryAll(criteria));
        Map
                <String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), PairTradeInfoDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<PairTradeInfo> queryAll(PairTradeInfoQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(PairTradeInfo.class, criteria));
    }


    @Override
    public void download(List
                                 <PairTradeInfoDto> all, HttpServletResponse response) throws IOException {
        List
                <Map
                        <String, Object>> list = new ArrayList<>();
        for (PairTradeInfoDto pairTradeInfo : all) {
            Map
                    <String, Object> map = new LinkedHashMap<>();
            map.put("日期", pairTradeInfo.getOpenDate());
            map.put("开盘价", pairTradeInfo.getOpenPrice());
            map.put("收盘价", pairTradeInfo.getClosePrice());
            map.put("最低价", pairTradeInfo.getLowPrice());
            map.put("最高价", pairTradeInfo.getHigh());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PairTradeInfo resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PairTradeInfo resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
