package com.pms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.entity.Resident;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pms
 * @since 2023-11-26
 */

@Mapper
public interface ResidentMapper extends BaseMapper<Resident> {

    IPage pageCC(Page<Resident> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
