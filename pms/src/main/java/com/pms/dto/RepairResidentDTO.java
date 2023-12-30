package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Repair和Resident的DTO对象", description="")
public class RepairResidentDTO {
    private int repairId;
    private int residentId;
    private String description;
    private String status;
    private LocalDateTime date;
    private String name;
    private String contact;
    private int communityId;
}
