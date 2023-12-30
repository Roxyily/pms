package com.pms.entity;

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
@ApiModel(value="Complaint对象", description="")
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "投诉主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "住户主键")
    private Integer residentId;

    @ApiModelProperty(value = "投诉内容")
    private String description;

    @ApiModelProperty(value = "投诉状态，0未处理，1已处理")
    private Integer status;

    @ApiModelProperty(value = "投诉日期")
    private LocalDateTime date;



}
