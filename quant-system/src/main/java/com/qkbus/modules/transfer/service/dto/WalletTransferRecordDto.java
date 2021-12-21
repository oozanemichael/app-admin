package com.qkbus.modules.transfer.service.dto;
import com.qkbus.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.sql.Timestamp;
    import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author 少林一枝花
* @date 2021-07-05
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class WalletTransferRecordDto  extends BaseDto implements Serializable {

            private Long id;

                @ApiModelProperty("接受人")
            private Long userId;

                @ApiModelProperty("划转类型")
            private Integer type;

                @ApiModelProperty("数量")
            private BigDecimal amount;

                @ApiModelProperty("币种")
            private String currency;

                @ApiModelProperty("手续费")
            private BigDecimal fee;

                @ApiModelProperty("手续费币种")
            private String feeCurrency;

                @ApiModelProperty("手续费利率")
            private BigDecimal feeRate;

                @ApiModelProperty("描述")
            private String comment;


}
