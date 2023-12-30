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
@ApiModel(value="PropertyMeter对象", description="")
public class PropertyMeter implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仪表主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "物业主键")
    private Integer propertyId;

    @ApiModelProperty(value = "仪表类型")
    private String type;

    @ApiModelProperty(value = "最后检查日期")
    private LocalDate lastInspectionDate;

    @ApiModelProperty(value = "下次检查日期")
    private LocalDate nextInspectionDate;

    @ApiModelProperty(value = "仪表状态，1正常，0故障")
    private Integer status;

    private String serialNumber;
}
