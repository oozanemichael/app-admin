
package com.qkbus.modules.assets.service;

import com.qkbus.common.service.BaseService;
import com.qkbus.modules.assets.domain.MemberWallet;
import com.qkbus.modules.assets.service.dto.MemberWalletDto;
import com.qkbus.modules.assets.service.dto.MemberWalletLogParam;
import com.qkbus.modules.assets.service.dto.MemberWalletQueryCriteria;
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
public interface MemberWalletService extends BaseService<MemberWallet> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map
     * <String,Object>
     */
    Map<String, Object> queryAll(MemberWalletQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     * <MemberWalletDto>
     */
    List<MemberWallet> queryAll(MemberWalletQueryCriteria criteria);

    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<MemberWalletDto> all, HttpServletResponse response) throws IOException;

    /**
     * 创建
     *
     * @param resources /
     * @return MemberWalletDto
     */
    void create(MemberWallet resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(MemberWallet resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Set<Long> ids);


    /*
     * 修改资产
     *
     * */
    void updateWallet(MemberWalletLogParam memberWalletLogParam);


    /*
     * 获取我的资产
     * */
    MemberWallet getMyWallet(Long uid, String currency);

}
