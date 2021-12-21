
package com.qkbus.modules.wallet.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.exception.BadRequestException;
import com.qkbus.modules.wallet.common.domain.CollectTask;
import com.qkbus.modules.wallet.common.service.CollectTaskService;
import com.qkbus.modules.wallet.common.service.dto.CollectTaskDto;
import com.qkbus.modules.wallet.common.service.dto.CollectTaskQueryCriteria;
import com.qkbus.modules.wallet.common.service.mapper.CollectTaskMapper;
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
public class CollectTaskServiceImpl extends BaseServiceImpl<CollectTaskMapper, CollectTask> implements CollectTaskService {

    private final IGenerator generator;

    @Override

    public Map<String, Object> queryAll(CollectTaskQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<CollectTask> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), CollectTaskDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }

    @Override
    public List<CollectTask> queryAll(CollectTaskQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(CollectTask.class, criteria));
    }


    @Override
    public void download(List<CollectTaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CollectTaskDto CollectTask : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("公链", CollectTask.getChain());
            map.put("任务类型", CollectTask.getTaskType());
            map.put("归集描述", CollectTask.getRemark());
            map.put("开始区块号", CollectTask.getBeginNo());
            map.put("结束区块号", CollectTask.getEndNo());
            map.put("状态", CollectTask.getStatus());
            map.put("创建时间", CollectTask.getGmtCreate());
            map.put("修改时间", CollectTask.getGmtUpdated());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CollectTask resources) {

        CollectTask one = this.query().eq("task_type", resources.getTaskType()).eq("status", 0).one();
        if (ObjectUtil.isNotNull(one)) {
            throw new BadRequestException("存在为进行的任务,请稍后再试!");
        }
        if (ObjectUtil.isNotNull(resources.getBeginNo()) && ObjectUtil.isNotNull(resources.getEndNo())) {
            if (resources.getEndNo() < resources.getBeginNo()) {
                throw new BadRequestException("结束区块不能小于开始区块");
            }
            //startNum 和 endNum 之间相差超过100时抛出此异常。
            if (resources.getEndNo() - resources.getBeginNo() > 100) {
                throw new BadRequestException("结束区块与开始区块不能相差大于100");
            }
        }
        this.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CollectTask resources) {
        if (ObjectUtil.isNotNull(resources.getBeginNo()) && ObjectUtil.isNotNull(resources.getEndNo())) {
            //startNum 和 endNum 之间相差超过100时抛出此异常。
            if (resources.getEndNo() - resources.getBeginNo() > 100) {
                throw new BadRequestException("结束区块与开始区块不能相差大于100");
            }
        }
        this.updateById(resources);
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        this.removeByIds(ids);
    }
}
