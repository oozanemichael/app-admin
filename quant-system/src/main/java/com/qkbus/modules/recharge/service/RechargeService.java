
package com.qkbus.modules.recharge.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.recharge.domain.Recharge;
import com.qkbus.modules.recharge.service.dto.RechargeDto;
import com.qkbus.modules.recharge.service.dto.RechargeQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */
public interface RechargeService extends BaseService<Recharge> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(RechargeQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <RechargeDto>
     */
    List<Recharge> queryAll(RechargeQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<RechargeDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return RechargeDto
     */
    void create(Recharge resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);


    //统计充值
    Map<String, Object> statisticalRecharge();

}
