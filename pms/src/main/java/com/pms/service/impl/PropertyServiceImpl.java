package com.pms.service.impl;

import com.pms.entity.Property;
import com.pms.mapper.PropertyMapper;
import com.pms.service.PropertyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

}
