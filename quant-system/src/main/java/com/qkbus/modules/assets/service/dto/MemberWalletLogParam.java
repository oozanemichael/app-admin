
package com.qkbus.modules.assets.service.dto;

import com.qkbus.modules.assets.domain.MemberWalletLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 少林一枝花
 * @date 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberWalletLogParam extends MemberWalletLog {

    @ApiModelProperty("冻结资产")
    private BigDecimal frozenBalance;

}
