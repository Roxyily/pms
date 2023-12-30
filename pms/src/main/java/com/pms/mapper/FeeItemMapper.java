package com.pms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.FeeItemPropertyDTO;
import com.pms.entity.FeeItem;
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
public interface FeeItemMapper extends BaseMapper<FeeItem> {

    IPage selectFeeItemsWithProperty(@Param("page") Page<FeeItemPropertyDTO> page, @Param("params")Map<String, Object> params);
}
