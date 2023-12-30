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
@ApiModel(value="Community对象", description="")
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小区主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小区名称")
    private String name;

    @ApiModelProperty(value = "小区地址")
    private String address;

    @ApiModelProperty(value = "物业经理")
    private String propertyManager;

    @ApiModelProperty(value = "联系电话")
    private String contact;


}
