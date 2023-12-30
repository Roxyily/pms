package com.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.entity.Resident;
import com.pms.mapper.ResidentMapper;
import com.pms.service.ResidentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pms
 * @since 2023-11-26
 */
@Service
public class ResidentServiceImpl extends ServiceImpl<ResidentMapper, Resident> implements ResidentService {

    @Resource
    private ResidentMapper residentMapper;

    @Override
    public IPage pageCC(Page<Resident> page, Wrapper wrapper) {
        return residentMapper.pageCC(page, wrapper);
    }
}
