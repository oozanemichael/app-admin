
package com.qkbus.modules.recharge.service.mapper;

import com.qkbus.common.mapper.CoreMapper;
import com.qkbus.modules.recharge.domain.Recharge;
import com.qkbus.modules.vo.StatisticalVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 少林一枝花
 * @date 2021-05-12
 */

public interface RechargeMapper extends CoreMapper<Recharge> {

    @Select("select a.gmt_create,ifNull(b.amount,0) as amount\n" +
            "        from (\n" +
            "        SELECT curdate() as gmt_create\n" +
            "        union all\n" +
            "        SELECT date_sub(curdate()\n" +
            "        , interval 1 day) as gmt_create\n" +
            "        union all\n" +
            "        SELECT date_sub(\n" +
            "        curdate()\n" +
            "        , interval 2 day) as gmt_create\n" +
            "        union all\n" +
            "        SELECT date_sub(\n" +
            "        curdate()\n" +
            "        , interval 3 day) as gmt_create\n" +
            "        union all\n" +
            "        SELECT date_sub(\n" +
            "        curdate()\n" +
            "        , interval 4 day) as gmt_create\n" +
            "        union all\n" +
            "        SELECT date_sub(\n" +
            "        curdate()\n" +
            "        , interval 5 day) as gmt_create\n" +
            "        union all\n" +
            "        SELECT date_sub(\n" +
            "        curdate()\n" +
            "        , interval 6 day) as gmt_create\n" +
            "        ) a left join (\n" +
            "        select date(gmt_create) as datetime, IFnull(sum(amount),0) as amount\n" +
            "        from recharge\n" +
            "        group by date(gmt_create)\n" +
            "        ) b on a.gmt_create = b.datetime\n" +
            "        ORDER BY a.gmt_create asc")
    List<StatisticalVo> statisticalWeek();

}
