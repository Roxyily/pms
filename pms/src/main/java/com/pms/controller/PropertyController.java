package com.pms.controller;


import com.pms.entity.Property;
import com.pms.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    /**
     * 根据社区ID获取对应的物业主键。
     *
     * @param communityId 社区ID
     * @return 如果找到对应的物业，返回物业主键；否则，返回null
     */
    public Integer getPropertyIdByCommunityId(Integer communityId) {
        // 查询property表以获取物业主键
        // 使用lambdaQuery方法查询property表以获取物业主键
        Property property = propertyService.lambdaQuery().eq(Property::getCommunityId, communityId).one();

        // 如果没有找到对应的物业，返回null
        if (property == null) {
            return null;
        }

        // 返回物业主键
        return property.getId();
    }
}
