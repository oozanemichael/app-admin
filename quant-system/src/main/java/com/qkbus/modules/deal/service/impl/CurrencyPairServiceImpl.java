package com.qkbus.modules.deal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.qkbus.constant.RedisConstant;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.deal.domain.CurrencyPair;
import com.qkbus.utils.RedisUtils;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.deal.service.CurrencyPairService;
import com.qkbus.modules.deal.service.dto.CurrencyPairDto;
import com.qkbus.modules.deal.service.dto.CurrencyPairQueryCriteria;
import com.qkbus.modules.deal.service.mapper.CurrencyPairMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import javax.servlet.ServletException;
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
public class CurrencyPairServiceImpl extends BaseServiceImpl<CurrencyPairMapper, CurrencyPair> implements CurrencyPairService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;


    @Override
    public Map<String, Object> queryAll(CurrencyPairQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<CurrencyPair> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), CurrencyPairDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<CurrencyPair> queryAll(CurrencyPairQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(CurrencyPair.class, criteria));
    }


    @Override
    public void download(List<CurrencyPairDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CurrencyPairDto currencyPair : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("展示名称", currencyPair.getDspName());
            map.put("基础货币", currencyPair.getBaseCurrency());
            map.put("计价货币", currencyPair.getQuoteCurrency());
            map.put("交易对名称", currencyPair.getKeyName());
            map.put("买单手续费", currencyPair.getTakerFee());
            map.put("卖单手续费", currencyPair.getMakerFee());
            map.put("开始交易时间", currencyPair.getOpenTime());
            map.put("状态", currencyPair.getStatus());
            map.put("排序", currencyPair.getCurrencyOrder());
            map.put("价格小数位", currencyPair.getPricePrecision());
            map.put("数量小数位", currencyPair.getVolumePrecision());
            map.put("限制买的上限", currencyPair.getBuyUpperLimit());
            map.put("开盘价", currencyPair.getOpenPrice());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CurrencyPair resources) {
        // 创建交易对
        String dspName = resources.getBaseCurrency() + "_" + resources.getQuoteCurrency();
        resources.setDspName(dspName);
        String keyName = resources.getBaseCurrency() + resources.getQuoteCurrency();
        resources.setKeyName(keyName);
        //查询交易对是否存在
        CurrencyPair dsp_name = this.query().eq("dsp_name", dspName).one();
        if(ObjectUtil.isNotNull(dsp_name)){
            throw new BadRequestException("该交易对:"+dsp_name+"已经存在!");
        }
        if(this.save(resources)){
         this.cacheCurrencyPair(resources,true);
        }
    }

    private  void  cacheCurrencyPair(CurrencyPair resources,Boolean flag){
        // 刷新Redis
        String key = RedisConstant.CURRENCY_PAIR + resources.getDspName();
        redisUtils.set(key, JSONObject.toJSON(resources));
        if(flag){
            // 初始化系统挂单信息，设置交易对的开盘价格等信息
            String tradeKey = RedisConstant.TRADE_INFO + DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN) +
                    ":" + resources.getDspName();
            redisUtils.hset(tradeKey, "close", resources.getOpenPrice());
            redisUtils.hset(tradeKey, "open", resources.getOpenPrice());
            redisUtils.hset(tradeKey, "low", resources.getOpenPrice());
            redisUtils.hset(tradeKey, "high", resources.getOpenPrice());
            redisUtils.hset(tradeKey, "amount", BigDecimal.ZERO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CurrencyPair resources) {
        if( this.updateById(resources)){
            this.cacheCurrencyPair(resources,false);
        }
    }

    @Override
    public void deleteAll(Set<Integer> ids) {
        this.removeByIds(ids);
    }
}
