package com.pms.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@ApiModel(value="MeterData对象", description="")
public class MeterData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "仪表主键")
    private Integer meterId;

    @ApiModelProperty(value = "数据属性")
    private String attribute;

    @ApiModelProperty(value = "数据值")
    private BigDecimal value;

    @ApiModelProperty(value = "数据日期")
    private LocalDateTime date;


}
