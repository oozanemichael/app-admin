
package com.qkbus.modules.wallet.tron.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.wallet.tron.domain.TrxAddress;
import com.qkbus.modules.wallet.tron.service.TrxAddressService;
import com.qkbus.modules.wallet.tron.service.dto.TrxAddressDto;
import com.qkbus.modules.wallet.tron.service.dto.TrxAddressQueryCriteria;
import com.qkbus.modules.wallet.tron.service.mapper.TrxAddressMapper;
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
 * @date 2021-05-15
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TrxAddressServiceImpl extends BaseServiceImpl<TrxAddressMapper, TrxAddress> implements TrxAddressService {

    private final IGenerator generator;

    @Override

    public Map<String, Object> queryAll(TrxAddressQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<TrxAddress> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), TrxAddressDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<TrxAddress> queryAll(TrxAddressQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(TrxAddress.class, criteria));
    }


    @Override
    public void download(List<TrxAddressDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TrxAddressDto TrxAddress : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", TrxAddress.getUserId());
            map.put("公链 ", TrxAddress.getCurrency());
            map.put("地址密码", TrxAddress.getPassword());
            map.put("地址", TrxAddress.getAddr());
            map.put("状态", TrxAddress.getStatus());
            map.put("创建时间", TrxAddress.getGmtCreate());
            map.put("修改时间", TrxAddress.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}
