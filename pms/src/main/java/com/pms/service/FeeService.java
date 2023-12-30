package com.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.FeeResidentItemDTO;
import com.pms.entity.Fee;
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
public interface FeeService extends IService<Fee> {

    IPage getFeesWithResidents(Page<FeeResidentItemDTO> page, Map<String, Object> params);
}
