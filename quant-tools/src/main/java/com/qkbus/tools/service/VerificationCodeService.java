
package com.qkbus.tools.service;

import com.qkbus.tools.domain.VerificationCode;
import com.qkbus.tools.domain.vo.EmailVo;

/**
 * @author 少林一枝花
 * @date 2021-12-26
 */
public interface VerificationCodeService {

    /**
     * 发送邮件验证码
     *
     * @param code 验证码
     * @return EmailVo
     */
    EmailVo sendEmail(VerificationCode code);

    /**
     * 验证
     *
     * @param code 验证码
     */
    void validated(VerificationCode code);
}
