package com.qkbus.modules.transfer.service.impl;

import com.qkbus.modules.transfer.domain.WalletTransferRecord;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.transfer.service.WalletTransferRecordService;
import com.qkbus.modules.transfer.service.dto.WalletTransferRecordDto;
import com.qkbus.modules.transfer.service.dto.WalletTransferRecordQueryCriteria;
import com.qkbus.modules.transfer.service.mapper.WalletTransferRecordMapper;
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
* @date 2021-07-05
*/
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WalletTransferRecordServiceImpl extends BaseServiceImpl
<WalletTransferRecordMapper, WalletTransferRecord> implements WalletTransferRecordService {

private final IGenerator generator;

@Override
public Map
<String, Object> queryAll(WalletTransferRecordQueryCriteria criteria, Pageable pageable) {
getPage(pageable);
PageInfo<WalletTransferRecord> page = new PageInfo<>(queryAll(criteria));
Map
<String, Object> map = new LinkedHashMap<>(2);
map.put("content", generator.convert(page.getList(), WalletTransferRecordDto.class));
map.put("totalElements", page.getTotal());
return map;
}


@Override
public List<WalletTransferRecord> queryAll(WalletTransferRecordQueryCriteria criteria){
return this.list(QueryHelpPlus.getPredicate(WalletTransferRecord.class, criteria));
}


@Override
public void download(List
<WalletTransferRecordDto> all, HttpServletResponse response) throws IOException {
    List
    <Map
    <String , Object>> list = new ArrayList<>();
        for (WalletTransferRecordDto walletTransferRecord : all) {
        Map
        <String ,Object> map = new LinkedHashMap<>();
                        map.put("接受人", walletTransferRecord.getUserId());
                        map.put("划转类型", walletTransferRecord.getType());
                        map.put("数量", walletTransferRecord.getAmount());
                        map.put("币种", walletTransferRecord.getCurrency());
                        map.put("手续费", walletTransferRecord.getFee());
                        map.put("手续费币种", walletTransferRecord.getFeeCurrency());
                        map.put("手续费利率", walletTransferRecord.getFeeRate());
                        map.put("描述", walletTransferRecord.getComment());
                        map.put("创建时间", walletTransferRecord.getGmtCreate());
                        map.put("修改时间", walletTransferRecord.getGmtUpdated());
            list.add(map);
            }
            FileUtil.downloadExcel(list, response);
            }

            @Override
            @Transactional(rollbackFor = Exception.class)
            public void create(WalletTransferRecord resources) {
            this.save(resources);
            }

            @Override
            @Transactional(rollbackFor = Exception.class)
            public void update(WalletTransferRecord resources) {
            this.updateById(resources);
            }

            @Override
            public void deleteAll(Set<Long> ids) {
            this.removeByIds(ids);
            }
            }
