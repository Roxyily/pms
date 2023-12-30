package com.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.PropertyMeterPropertyDTO;
import com.pms.entity.PropertyMeter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
public interface PropertyMeterService extends IService<PropertyMeter> {

    IPage getPropertyMetersWithProperty(Page<PropertyMeterPropertyDTO> page, Map<String, Object> params);
}
