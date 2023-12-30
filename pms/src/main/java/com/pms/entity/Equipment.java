package com.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Equipment对象", description="")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "物业主键")
    private Integer propertyId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备状态，1正常，0故障")
    private Integer status;

    @ApiModelProperty(value = "最后维修日期")
    private LocalDate lastMaintenance;

    private String serialNumber;

}
