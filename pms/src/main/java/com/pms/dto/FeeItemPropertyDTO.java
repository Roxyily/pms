package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * ClassName: FeeItemPropertyDTO
 * Package: com.pms.dto
 * Description:
 * Author: JingYin233
 * Create: 2023/12/15 - 21:49
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FeeItem和Property的DTO对象", description="")
public class FeeItemPropertyDTO {
    // FeeItem表字段
    private int feeItemId; // 收费项目主键
    private String feeItemName; // 收费项目名称
    private BigDecimal amount; // 收费金额

    // Property表字段
    private int propertyId; // 物业主键
    private String propertyName; // 物业名称
    private int communityId; // 小区主键
    private String contact; // 联系方式
    private String address; // 物业地址
}
