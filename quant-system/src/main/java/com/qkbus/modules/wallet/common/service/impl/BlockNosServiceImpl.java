
package com.qkbus.modules.wallet.common.service.impl;

import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.modules.wallet.common.domain.BlockNos;
import com.qkbus.modules.wallet.common.service.BlockNosService;
import com.qkbus.modules.wallet.common.service.dto.BlockNosDto;
import com.qkbus.modules.wallet.common.service.dto.BlockNosQueryCriteria;
import com.qkbus.modules.wallet.common.service.mapper.BlockNosMapper;
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
public class BlockNosServiceImpl extends BaseServiceImpl<BlockNosMapper, BlockNos> implements BlockNosService {

    private final IGenerator generator;

    @Override

    public Map<String, Object> queryAll(BlockNosQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<BlockNos> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), BlockNosDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override

    public List<BlockNos> queryAll(BlockNosQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(BlockNos.class, criteria));
    }


    @Override
    public void download(List<BlockNosDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BlockNosDto BlockNos : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("区块链名BTC,ETH...", BlockNos.getCurrency());
            map.put("最新查询的区块数", BlockNos.getChainNo());
            map.put("获取当前最新区块号", BlockNos.getNewestBlockNo());
            map.put("当前同步状态", BlockNos.getSyncStatus());
            map.put("当前归集状态", BlockNos.getGatherStatus());
            map.put("创建时间", BlockNos.getGmtCreate());
            map.put("修改时间", BlockNos.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void create(BlockNos resources) {
        this.save(resources);
        return;
    }

    @Override

    @Transactional(rollbackFor = Exception.class)
    public void update(BlockNos resources) {
        this.updateById(resources);
    }

    @Override

    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
