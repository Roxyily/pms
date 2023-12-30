package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.PrepaymentResidentDTO;
import com.pms.entity.Prepayment;
import com.pms.mapper.PrepaymentMapper;
import com.pms.service.PrepaymentService;
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
public class PrepaymentServiceImpl extends ServiceImpl<PrepaymentMapper, Prepayment> implements PrepaymentService {

    @Resource
    private PrepaymentMapper prepaymentMapper;

    @Override
    public IPage getPrepaymentsWithResident(Page<PrepaymentResidentDTO> page, Map<String, Object> params) {
        return prepaymentMapper.selectPrepaymentsWithResident(page, params);
    }
}
