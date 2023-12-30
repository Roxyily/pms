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
@ApiModel(value="Fee对象", description="")
public class Fee implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "住户主键")
    private Integer residentId;

    @ApiModelProperty(value = "物业主键")
    private Integer propertyId;

    @ApiModelProperty(value = "收费项目主键")
    private Integer feeItemId;

    @ApiModelProperty(value = "收费日期")
    private LocalDate date;

    @ApiModelProperty(value = "支付状态，0未支付，1已支付")
    private Integer status;


}
