
package com.qkbus.modules.assets.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.assets.domain.MemberWalletLog;
import com.qkbus.modules.assets.service.dto.MemberWalletLogDto;
import com.qkbus.modules.assets.service.dto.MemberWalletLogQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
public interface MemberWalletLogService extends BaseService<MemberWalletLog> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(MemberWalletLogQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <MemberWalletLogDto>
     */
    List<MemberWalletLog> queryAll(MemberWalletLogQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MemberWalletLogDto> all, HttpServletResponse response) throws IOException;


}
