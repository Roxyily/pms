package com.pms.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ClassName: ComplaintResidentDTO
 * Package: com.pms.dto
 * Description:
 * Author: JingYin233
 * Create: 2023/12/15 - 11:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Complaint和Resident的DTO对象", description="")
public class ComplaintResidentDTO {
    private int complaintId;
    private int residentId;
    private String description;
    private String status;
    private LocalDateTime date;
    private String name;
    private String contact;
    private int communityId;

}
