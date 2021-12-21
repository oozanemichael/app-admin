
package com.qkbus.modules.transfer.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.transfer.domain.TransferRecord;
import com.qkbus.modules.transfer.service.TransferRecordService;
import com.qkbus.modules.transfer.service.dto.TransferRecordDto;
import com.qkbus.modules.transfer.service.dto.TransferRecordQueryCriteria;
import com.qkbus.modules.transfer.service.mapper.TransferRecordMapper;
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
public class TransferRecordServiceImpl extends BaseServiceImpl<TransferRecordMapper, TransferRecord> implements TransferRecordService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(TransferRecordQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<TransferRecord> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), TransferRecordDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<TransferRecord> queryAll(TransferRecordQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(TransferRecord.class, criteria));
    }


    @Override
    public void download(List<TransferRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TransferRecordDto TransferRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("转出人", TransferRecord.getFromUserId());
            map.put("接受人", TransferRecord.getToUserId());
            map.put("转出地址", TransferRecord.getFromAddress());
            map.put("接收地址", TransferRecord.getToAddress());
            map.put("转出账号", TransferRecord.getFromAccount());
            map.put("转入账号", TransferRecord.getToAccount());
            map.put("数量", TransferRecord.getAmount());
            map.put("币种", TransferRecord.getCurrency());
            map.put("手续费", TransferRecord.getFee());
            map.put("手续费币种", TransferRecord.getFeeCurrency());
            map.put("手续费利率", TransferRecord.getFeeRate());
            map.put("创建时间", TransferRecord.getGmtCreate());
            map.put("修改时间", TransferRecord.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
