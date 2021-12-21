package com.qkbus.modules.mine.service.impl;

import com.qkbus.modules.mine.domain.MineOrders;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.mine.service.MineOrdersService;
import com.qkbus.modules.mine.service.dto.MineOrdersDto;
import com.qkbus.modules.mine.service.dto.MineOrdersQueryCriteria;
import com.qkbus.modules.mine.service.mapper.MineOrdersMapper;
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
public class MineOrdersServiceImpl extends BaseServiceImpl<MineOrdersMapper, MineOrders> implements MineOrdersService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(MineOrdersQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<MineOrders> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MineOrdersDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<MineOrders> queryAll(MineOrdersQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(MineOrders.class, criteria));
    }


    @Override
    public void download(List<MineOrdersDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MineOrdersDto mineOrders : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("购买的用户ID", mineOrders.getUserId());
            map.put("矿机ID", mineOrders.getMineId());
            map.put("矿机名称", mineOrders.getMineName());
            map.put("矿机图片", mineOrders.getMineImage());
            map.put("等级", mineOrders.getLevel());
            map.put("矿机价格", mineOrders.getPrice());
            map.put("折合usdt金额 总金额", mineOrders.getUsdtPrice());
            map.put("利率", mineOrders.getRate());
            map.put("剩余释放量", mineOrders.getFissionAmount());
            map.put("复投金额", mineOrders.getPluralAmount());
            map.put("复投金额", mineOrders.getPluralUsdtAmount());
            map.put("已释放总数", mineOrders.getReleaseAmount());
            map.put("今天释放金额", mineOrders.getNowReleaseAmount());
            map.put("释放币种", mineOrders.getReleaseCurrency());
            map.put("释放次数", mineOrders.getReleaseNum());
            map.put("周期", mineOrders.getPeriod());
            map.put("算力", mineOrders.getHashrate());
            map.put("矿机类型   0是正常  1是体验", mineOrders.getMineType());
            map.put("是否到期", mineOrders.getEnabled());
            map.put("结束日期", mineOrders.getEndAt());
            map.put("矿机类型   0是正常  1是体验", mineOrders.getGmtCreate());
            map.put(" gmtUpdated", mineOrders.getGmtUpdated());
            map.put(" version", mineOrders.getVersion());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MineOrders resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MineOrders resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
