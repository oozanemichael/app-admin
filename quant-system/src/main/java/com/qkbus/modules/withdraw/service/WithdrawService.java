
package com.qkbus.modules.withdraw.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.withdraw.domain.Withdraw;
import com.qkbus.modules.withdraw.service.dto.WithdrawDto;
import com.qkbus.modules.withdraw.service.dto.WithdrawQueryCriteria;
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
public interface WithdrawService extends BaseService<Withdraw> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(WithdrawQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <WithdrawDto>
     */
    List<Withdraw> queryAll(WithdrawQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<WithdrawDto> all, HttpServletResponse response) throws IOException;


    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Withdraw resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);


    /*
     * 审核提币
     * */
    void audit(Withdraw withdraw);


    //统计提币记录 最近七天的  和总数
    Map<String, Object> statisticalWithdraw();


}
