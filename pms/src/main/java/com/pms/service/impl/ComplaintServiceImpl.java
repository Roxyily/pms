package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.ComplaintResidentDTO;
import com.pms.dto.RepairResidentDTO;
import com.pms.entity.Complaint;
import com.pms.mapper.ComplaintMapper;
import com.pms.service.ComplaintService;
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
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

    @Resource
    private ComplaintMapper complaintMapper;

    @Override
    public IPage getComplaintsWithResidents(Page<ComplaintResidentDTO> page, Map<String, Object> params) {
        return complaintMapper.selectComplaintsWithResidents(page, params);
    }
}
