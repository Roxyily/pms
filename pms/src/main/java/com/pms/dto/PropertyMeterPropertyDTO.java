package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PropertyMeter和PropertyMeter的DTO对象", description="")
public class PropertyMeterPropertyDTO {
    // property 表的字段
    private int propertyId;
    private String propertyName;
    private int communityId;
    private String contact;
    private String address;

    // property_meter 表的字段
    private int id;
    private String type;
    private LocalDate lastInspectionDate;
    private LocalDate nextInspectionDate;
    private int status;
    private String serialNumber;
}