package com.pms.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Resident对象", description="")
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "住户主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "住户姓名")
    private String name;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "小区主键")
    private Integer communityId;

    @ApiModelProperty(value = "单元号")
    private String unitNumber;

    @ApiModelProperty(value = "房间号")
    private String roomNumber;

    @ApiModelProperty(value = "入住日期")
    private LocalDate moveInDate;

    @ApiModelProperty(value = "离开日期")
    private LocalDate moveOutDate;

}
