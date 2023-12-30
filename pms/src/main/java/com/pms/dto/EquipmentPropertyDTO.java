package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Equipment和Property的DTO对象", description="")
public class EquipmentPropertyDTO {
    // Equipment表字段
    private int equipmentId; // 设备主键
    private int propertyId; // 物业主键
    private String equipmentName; // 设备名称
    private int equipmentStatus; // 设备状态，1正常，0故障
    private LocalDateTime lastMaintenance; // 最后维修日期
    private String serialNumber;

    // Property表字段
    private String propertyName; // 物业名称
    private int communityId; // 小区主键
    private String contact; // 联系方式
    private String address; // 物业地址
}