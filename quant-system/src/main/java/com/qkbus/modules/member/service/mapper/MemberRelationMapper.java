
package com.qkbus.modules.member.service.mapper;

import com.qkbus.common.mapper.CoreMapper;
import com.qkbus.modules.member.domain.MemberRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */

public interface MemberRelationMapper extends CoreMapper<MemberRelation> {

    List<MemberRelation> getRelationMaxByParentIds(@Param("set") Object o);


}
