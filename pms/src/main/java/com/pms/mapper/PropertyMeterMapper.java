package com.pms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.PropertyMeterPropertyDTO;
import com.pms.entity.PropertyMeter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */

@Mapper
public interface PropertyMeterMapper extends BaseMapper<PropertyMeter> {

    IPage selectPropertyMetersWithProperty(@Param("page") Page<PropertyMeterPropertyDTO> page, @Param("params")Map<String, Object> params);
}
