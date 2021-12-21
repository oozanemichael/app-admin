package com.qkbus.modules.mine.service.impl;

import com.qkbus.modules.mine.domain.MineRecord;
import lombok.AllArgsConstructor;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.dozer.service.IGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.utils.FileUtil;
import com.qkbus.modules.mine.service.MineRecordService;
import com.qkbus.modules.mine.service.dto.MineRecordDto;
import com.qkbus.modules.mine.service.dto.MineRecordQueryCriteria;
import com.qkbus.modules.mine.service.mapper.MineRecordMapper;
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
public class MineRecordServiceImpl extends BaseServiceImpl<MineRecordMapper, MineRecord> implements MineRecordService {

    private final IGenerator generator;

    @Override
    public Map<String, Object> queryAll(MineRecordQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<MineRecord> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), MineRecordDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<MineRecord> queryAll(MineRecordQueryCriteria criteria) {
        return this.list(QueryHelpPlus.getPredicate(MineRecord.class, criteria));
    }


    @Override
    public void download(List<MineRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MineRecordDto mineRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("会员id", mineRecord.getUserId());
            map.put("推荐人id", mineRecord.getFromUserId());
            map.put("自己变化的原因", mineRecord.getUserChangeId());
            map.put("对方变化的原因", mineRecord.getFromUserChangeId());
            map.put("金额", mineRecord.getCount());
            map.put("开始金额", mineRecord.getStartCount());
            map.put("结束金额", mineRecord.getEndCount());
            map.put("币种类型", mineRecord.getCurrency());
            map.put("平台释放个数", mineRecord.getReleasePlatform());
            map.put("价格", mineRecord.getCloseRate());
            map.put("利率", mineRecord.getRate());
            map.put("描述", mineRecord.getComment());
            map.put("类型", mineRecord.getRecordType());
            map.put("其他", mineRecord.getPartnerNum());
            map.put("分红状态", mineRecord.getTeamStatus());
            map.put("创建时间", mineRecord.getGmtCreate());
            map.put("修改时间", mineRecord.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MineRecord resources) {
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MineRecord resources) {
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Integer> ids) {
        this.removeByIds(ids);
    }
}
