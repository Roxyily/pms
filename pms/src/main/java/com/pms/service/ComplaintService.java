package com.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.ComplaintResidentDTO;
import com.pms.dto.RepairResidentDTO;
import com.pms.entity.Complaint;
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
public interface ComplaintService extends IService<Complaint> {

    IPage getComplaintsWithResidents(Page<ComplaintResidentDTO> page, Map<String, Object> params);
}
