
package com.qkbus.modules.wallet.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.wallet.common.domain.Currency;
import com.qkbus.modules.wallet.common.service.CurrencyService;
import com.qkbus.modules.wallet.common.service.dto.CurrencyDto;
import com.qkbus.modules.wallet.common.service.dto.CurrencyQueryCriteria;
import com.qkbus.modules.wallet.common.service.mapper.CurrencyMapper;
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
public class CurrencyServiceImpl extends BaseServiceImpl<CurrencyMapper, Currency> implements CurrencyService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(CurrencyQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<Currency> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), CurrencyDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<Currency> queryAll(CurrencyQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Currency.class, criteria));
    }


    @Override
    public void download(List<CurrencyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CurrencyDto Currency : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("币种", Currency.getCurrency());
            map.put("货币名称", Currency.getCurrencyName());
            map.put("最小充值额", Currency.getMinRecharge());
            map.put("公链", Currency.getChain());
            map.put("是否可充值", Currency.getCanRecharge());
            map.put("合约", Currency.getTokenAddr());
            map.put("代币精度", Currency.getCurrencyDecimal());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Currency resources) {
        //查询是否存在
        Currency one = this.query().eq("chain", resources.getChain()).eq("currency", resources.getCurrency()).one();
        if (ObjectUtil.isNotNull(one)) {
            throw new BadRequestException("该公链存在币种:" + resources.getCurrency());
        }
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Currency resources) {
        //查询是否存在
        Currency one = this.query().eq("chain", resources.getChain()).eq("currency", resources.getCurrency()).one();
        if (ObjectUtil.isNotNull(one)) {
            if (!one.getId().equals(resources.getId())) {
                throw new BadRequestException("该公链存在币种:" + resources.getCurrency());
            }
        }
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Integer> ids) {
        this.removeByIds(ids);
    }
}
