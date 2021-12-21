package com.qkbus.modules.deal.service.impl;

import com.qkbus.modules.deal.domain.PairOrderDeal;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.deal.service.PairOrderDealService;
import com.qkbus.modules.deal.service.dto.PairOrderDealDto;
import com.qkbus.modules.deal.service.dto.PairOrderDealQueryCriteria;
import com.qkbus.modules.deal.service.mapper.PairOrderDealMapper;
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
public class PairOrderDealServiceImpl extends BaseServiceImpl
        <PairOrderDealMapper, PairOrderDeal> implements PairOrderDealService {

    private final IGenerator generator;

    @Override
    public Map
            <String, Object> queryAll(PairOrderDealQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<PairOrderDeal> page = new PageInfo<>(queryAll(criteria));
        Map
                <String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), PairOrderDealDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<PairOrderDeal> queryAll(PairOrderDealQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(PairOrderDeal.class, criteria));
    }


    @Override
    public void download(List
                                 <PairOrderDealDto> all, HttpServletResponse response) throws IOException {
        List
                <Map
                        <String, Object>> list = new ArrayList<>();
        for (PairOrderDealDto pairOrderDeal : all) {
            Map
                    <String, Object> map = new LinkedHashMap<>();
            map.put("订单类型", pairOrderDeal.getOrderType());
            map.put("交易对", pairOrderDeal.getCurrencyPair());
            map.put("订单号", pairOrderDeal.getOrderNo());
            map.put("购买的委托单号", pairOrderDeal.getBuyOrderNo());
            map.put("出售的委托单号", pairOrderDeal.getSellOrderNo());
            map.put("购买人", pairOrderDeal.getBuyUser());
            map.put("出售人", pairOrderDeal.getSellUser());
            map.put("撮合价格", pairOrderDeal.getOrderPrice());
            map.put("撮合数量", pairOrderDeal.getOrderNum());
            map.put("撮合时间", pairOrderDeal.getGmtCreate());
            map.put("卖单手续费", pairOrderDeal.getSellFee());
            map.put("买单手续费", pairOrderDeal.getBuyFee());
            map.put("是否已经加入锁仓释放记录，0：未加入 1：已加入", pairOrderDeal.getLockReleaseFlag());
            map.put("手续费分红记录 0:待分红 1:已分红 2:不分红", pairOrderDeal.getFeeProfitFlag());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PairOrderDeal resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PairOrderDeal resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
