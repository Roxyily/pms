package com.pms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.RepairResidentDTO;
import com.pms.entity.Repair;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pms.entity.Resident;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
public interface RepairService extends IService<Repair> {

    IPage getRepairsWithResidents(Page<RepairResidentDTO> page, Map<String, Object> params);
}
