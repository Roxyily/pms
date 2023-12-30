package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.PropertyMeterPropertyDTO;
import com.pms.entity.PropertyMeter;
import com.pms.mapper.PropertyMeterMapper;
import com.pms.service.PropertyMeterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
@Service
public class PropertyMeterServiceImpl extends ServiceImpl<PropertyMeterMapper, PropertyMeter> implements PropertyMeterService {

    @Resource
    private PropertyMeterMapper propertyMeterMapper;
    @Override
    public IPage getPropertyMetersWithProperty(Page<PropertyMeterPropertyDTO> page, Map<String, Object> params) {
        return propertyMeterMapper.selectPropertyMetersWithProperty(page, params);
    }
}
