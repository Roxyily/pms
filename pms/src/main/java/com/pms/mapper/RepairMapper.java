package com.pms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.RepairResidentDTO;
import com.pms.entity.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pms.entity.Resident;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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
public interface RepairMapper extends BaseMapper<Repair> {

    IPage  selectRepairsWithResidents(@Param("page") Page<RepairResidentDTO> page, @Param("params") Map<String, Object> params);
}
