package com.qkbus.modules.transfer.domain;
import com.baomidou.mybatisplus.annotation.*;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
    import javax.validation.constraints.*;
    import java.sql.Timestamp;
    import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author 少林一枝花
* @date 2021-07-05
*/
@Data
@TableName("wallet_transfer_record")
public class WalletTransferRecord  extends BaseEntity  implements Serializable {
            @TableId(value = "id", type = IdType.AUTO)
        private Long id;
            @ApiModelProperty(value ="接受人")
        private Long userId;
            @ApiModelProperty(value ="划转类型")
        private Integer type;
            @ApiModelProperty(value ="数量")
        private BigDecimal amount;
            @ApiModelProperty(value ="币种")
        private String currency;
            @ApiModelProperty(value ="手续费")
        private BigDecimal fee;
            @ApiModelProperty(value ="手续费币种")
        private String feeCurrency;
            @ApiModelProperty(value ="手续费利率")
        private BigDecimal feeRate;
            @ApiModelProperty(value ="描述")
        private String comment;
            @ApiModelProperty(value ="创建时间")
                @TableField(fill= FieldFill.INSERT)
        private Timestamp gmtCreate;
            @ApiModelProperty(value ="修改时间")
                @TableField(fill= FieldFill.INSERT_UPDATE)
        private Timestamp gmtUpdated;

public void copy(WalletTransferRecord source){
BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
}
}
