
package com.qkbus.modules.assets.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.assets.domain.MemberWalletLog;
import com.qkbus.modules.assets.service.MemberWalletLogService;
import com.qkbus.modules.assets.service.dto.MemberWalletLogDto;
import com.qkbus.modules.assets.service.dto.MemberWalletLogQueryCriteria;
import com.qkbus.modules.assets.service.mapper.MemberWalletLogMapper;
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
 * @date 2021-05-11
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MemberWalletLogServiceImpl extends BaseServiceImpl<MemberWalletLogMapper, MemberWalletLog> implements MemberWalletLogService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(MemberWalletLogQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<MemberWalletLog> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MemberWalletLogDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<MemberWalletLog> queryAll(MemberWalletLogQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(MemberWalletLog.class, criteria));
    }


    @Override
    public void download(List<MemberWalletLogDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberWalletLogDto MemberWalletLog : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("UID", MemberWalletLog.getUid());
            map.put("币种", MemberWalletLog.getCurrency());
            map.put("金额", MemberWalletLog.getAmount());
            map.put("出账前金额", MemberWalletLog.getBeforeAmount());
            map.put("出账后金额", MemberWalletLog.getAfterAmount());
            map.put("类型", MemberWalletLog.getLogType());
            map.put("创建时间", MemberWalletLog.getGmtCreate());
            map.put("备注", MemberWalletLog.getComment());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}
