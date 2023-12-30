package com.pms.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value="FeeItem对象", description="")
public class FeeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "物业主键")
    private Integer propertyId;

    @ApiModelProperty(value = "收费项目名称")
    private String name;

    @ApiModelProperty(value = "收费金额")
    private BigDecimal amount;


}
