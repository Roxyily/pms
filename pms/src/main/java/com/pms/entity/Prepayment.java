package com.pms.entity;

import java.math.BigDecimal;
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
@ApiModel(value="Prepayment对象", description="")
public class Prepayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预付款主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "住户主键")
    private Integer residentId;

    @ApiModelProperty(value = "物业主键")
    private Integer propertyId;

    @ApiModelProperty(value = "预付款金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "预付款日期")
    private LocalDate date;


}
