
package com.qkbus.logging.service.mapper;

import com.qkbus.common.mapper.CoreMapper;
import com.qkbus.logging.domain.SysLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 少林一枝花
 * @date 2019-5-22
 */

@Mapper
@Repository
public interface LogMapper extends CoreMapper<SysLog> {


    @Select("<script>select l.id,l.gmt_create as gmtCreate,l.description, l.request_ip as requestIp,l.addressfrom sys_log l  " +
            "  where l.type=1 " +
            " <if test = \"nickname !=null\"> and u.nickname LIKE CONCAT('%',#{nickname},'%')</if> order by l.id desc</script>")
    List<SysLog> findAllByPageable(@Param("nickname") String nickname);

    @Select("select count(*) FROM (select request_ip FROM sys_log where gmt_create between #{date1} and #{date2} GROUP BY request_ip) as s")
    long findIp(@Param("date1") String date1, @Param("date2") String date2);
}
