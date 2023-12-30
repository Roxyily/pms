package com.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.PrepaymentResidentDTO;
import com.pms.entity.Prepayment;
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
public interface PrepaymentService extends IService<Prepayment> {

    IPage getPrepaymentsWithResident(Page<PrepaymentResidentDTO> page, Map<String, Object> params);
}
