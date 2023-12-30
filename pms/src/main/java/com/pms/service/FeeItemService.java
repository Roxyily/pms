package com.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.FeeItemPropertyDTO;
import com.pms.entity.FeeItem;
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
public interface FeeItemService extends IService<FeeItem> {

    IPage getFeeItemsWithProperty(Page<FeeItemPropertyDTO> page, Map<String, Object> params);
}
