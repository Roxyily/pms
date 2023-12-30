package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.FeeResidentItemDTO;
import com.pms.entity.Fee;
import com.pms.mapper.FeeMapper;
import com.pms.service.FeeService;
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
public class FeeServiceImpl extends ServiceImpl<FeeMapper, Fee> implements FeeService {

    @Resource
    private FeeMapper feeMapper;

    @Override
    public IPage getFeesWithResidents(Page<FeeResidentItemDTO> page, Map<String, Object> params) {
        return feeMapper.selectFeesWithResidentsAndItems(page, params);
    }
}
