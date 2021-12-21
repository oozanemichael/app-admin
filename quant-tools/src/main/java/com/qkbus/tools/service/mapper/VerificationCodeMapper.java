
package com.qkbus.tools.service.mapper;


import com.qkbus.common.mapper.CoreMapper;
import com.qkbus.tools.domain.VerificationCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VerificationCodeMapper extends CoreMapper<VerificationCode> {

}
