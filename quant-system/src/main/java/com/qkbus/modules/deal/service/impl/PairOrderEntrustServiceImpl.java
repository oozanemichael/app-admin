package com.qkbus.modules.deal.service.impl;

import com.qkbus.modules.deal.domain.PairOrderEntrust;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.deal.service.PairOrderEntrustService;
import com.qkbus.modules.deal.service.dto.PairOrderEntrustDto;
import com.qkbus.modules.deal.service.dto.PairOrderEntrustQueryCriteria;
import com.qkbus.modules.deal.service.mapper.PairOrderEntrustMapper;
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
public class PairOrderEntrustServiceImpl extends BaseServiceImpl
        <PairOrderEntrustMapper, PairOrderEntrust> implements PairOrderEntrustService {

    private final IGenerator generator;

    @Override
    public Map
            <String, Object> queryAll(PairOrderEntrustQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<PairOrderEntrust> page = new PageInfo<>(queryAll(criteria));
        Map
                <String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), PairOrderEntrustDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<PairOrderEntrust> queryAll(PairOrderEntrustQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(PairOrderEntrust.class, criteria));
    }


    @Override
    public void download(List<PairOrderEntrustDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PairOrderEntrustDto pairOrderEntrust : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("交易对", pairOrderEntrust.getCurrencyPair());
            map.put("委托订单号", pairOrderEntrust.getOrderId());
            map.put("0: 买 1：卖", pairOrderEntrust.getOrderType());
            map.put("挂单金额", pairOrderEntrust.getOrderPrice());
            map.put("挂单数量", pairOrderEntrust.getOrderNum());
            map.put("未撮合数量", pairOrderEntrust.getBalanceNum());
            map.put("挂单时间", pairOrderEntrust.getGmtCreate());
            map.put("挂单人", pairOrderEntrust.getCreateBy());
            map.put("订单状态", pairOrderEntrust.getStatus());
            map.put("手续费", pairOrderEntrust.getFee());
            map.put("未撮合的手续费", pairOrderEntrust.getBalanceFee());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PairOrderEntrust resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PairOrderEntrust resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
