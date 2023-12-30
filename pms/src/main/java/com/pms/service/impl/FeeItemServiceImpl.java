package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.FeeItemPropertyDTO;
import com.pms.entity.FeeItem;
import com.pms.mapper.FeeItemMapper;
import com.pms.service.FeeItemService;
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
public class FeeItemServiceImpl extends ServiceImpl<FeeItemMapper, FeeItem> implements FeeItemService {

    @Resource
    private FeeItemMapper feeItemMapper;

    @Override
    public IPage getFeeItemsWithProperty(Page<FeeItemPropertyDTO> page, Map<String, Object> params) {
        return feeItemMapper.selectFeeItemsWithProperty(page, params);
    }
}
