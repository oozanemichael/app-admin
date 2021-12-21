
package com.qkbus.modules.wallet.tron.service.dto;

import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 少林一枝花
 * @date 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TrxAddressDto extends BaseDto implements Serializable {
    private Long id;
    @ApiModelProperty("UID")
    private Long userId;
    @ApiModelProperty("公链 ")
    private String currency;
    @ApiModelProperty("地址密码")
    private String password;
    @ApiModelProperty("地址")
    private String addr;
    @ApiModelProperty("状态")
    private Integer status;

}
