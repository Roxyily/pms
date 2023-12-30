package com.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.dto.EquipmentPropertyDTO;
import com.pms.entity.Equipment;
import com.pms.mapper.EquipmentMapper;
import com.pms.service.EquipmentService;
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
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Override
    public IPage getEquipmentsWithProperty(Page<EquipmentPropertyDTO> page, Map<String, Object> params) {
        return equipmentMapper.selectEquipmentsWithProperty(page, params);
    }
}
