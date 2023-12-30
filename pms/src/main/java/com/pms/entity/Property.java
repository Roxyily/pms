package com.pms.entity;

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
@ApiModel(value="Property对象", description="")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物业主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "物业名称")
    private String name;

    @ApiModelProperty(value = "小区主键")
    private Integer communityId;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "物业地址")
    private String address;


}
