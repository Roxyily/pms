package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ClassName: FeeResidentItemDTO
 * Package: com.pms.dto
 * Description:
 * Author: JingYin233
 * Create: 2023/12/15 - 15:33
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Fee, Resident和FeeItem的DTO对象", description="")
public class FeeResidentItemDTO {
    // Fee表字段
    private int feeId; // 收费主键
    private int residentId; // 住户主键
    private int propertyId; // 物业主键
    private int feeItemId; // 收费项目主键
    private LocalDate feeDate; // 收费日期
    private int status; // 支付状态，0未支付，1已支付

    // Resident表字段
    private String residentName; // 住户姓名
    private String contact; // 联系方式
    private int communityId; // 小区主键

    // FeeItem表字段
    private String feeItemName; // 收费项目名称
    private BigDecimal amount; // 收费金额
}

