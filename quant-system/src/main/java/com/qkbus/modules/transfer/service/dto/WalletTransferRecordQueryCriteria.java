package com.qkbus.modules.transfer.service.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
    import java.sql.Timestamp;
    import java.util.List;
    import com.qkbus.annotation.Query;

/**
* @author 少林一枝花
* @date 2021-07-05
*/
@Data
public class WalletTransferRecordQueryCriteria{
            @Query
                @ApiModelProperty(value ="接受人")
            private Long userId;
            @Query
                @ApiModelProperty(value ="币种")
            private String currency;

        @Query(type = Query.Type.BETWEEN)
        private List<Timestamp> gmtCreate;
}
