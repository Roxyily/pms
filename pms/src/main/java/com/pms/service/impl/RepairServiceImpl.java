package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.RepairResidentDTO;
import com.pms.entity.Repair;
import com.pms.mapper.RepairMapper;
import com.pms.service.RepairService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {

    @Resource
    private RepairMapper repairMapper;


    @Override
    public IPage getRepairsWithResidents(Page<RepairResidentDTO> page, Map<String, Object> params) {
        return repairMapper.selectRepairsWithResidents(page, params);
    }
}
