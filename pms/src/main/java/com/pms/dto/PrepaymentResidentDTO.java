package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Prepayment和Resident的DTO对象", description="")
public class PrepaymentResidentDTO {
    // Prepayment表字段
    private int prepaymentId; // 预付款主键
    private int residentId; // 住户主键
    private int propertyId; // 物业主键
    private BigDecimal amount; // 预付款金额
    private LocalDate prepaymentDate; // 预付款日期

    // Resident表字段
    private String residentName; // 住户姓名
    private String contact; // 联系方式
    private int communityId; // 小区主键
    private String unitNumber; // 单元号
    private String roomNumber; // 房间号
    private LocalDate moveInDate; // 入住日期
    private LocalDate moveOutDate; // 离开日期
}