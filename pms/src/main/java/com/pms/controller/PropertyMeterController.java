package com.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.PropertyMeterPropertyDTO;
import com.pms.entity.MeterData;
import com.pms.entity.PropertyMeter;
import com.pms.entity.User;
import com.pms.service.MeterDataService;
import com.pms.service.PropertyMeterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pms
 * @since 2023-12-10
 */
@RestController
@RequestMapping("/property-meter")
public class PropertyMeterController {
    @Autowired
    private PropertyMeterService propertyMeterService;

    @Autowired
    private MeterDataService meterDataService;

    @Transactional
    @ApiOperation(value = "删除", notes = "根据Id删除单条记录,同时删除相关的数据信息")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        // 先删除meter_data表中的相关记录
        LambdaQueryWrapper<MeterData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MeterData::getMeterId, id);
        meterDataService.remove(lambdaQueryWrapper);

        // 然后删除property_meter表中的记录
        return propertyMeterService.removeById(id);
    }


    @ApiOperation(value = "更新", notes = "根据Id更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody PropertyMeter propertyMeter, HttpSession session) {
        return propertyMeterService.updateById(propertyMeter)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "查询分页", notes = "")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpSession session) {
        HashMap hashMap = query.getParam();
        String equipmentName = (String) hashMap.get("equipmentName");
        String equipmentStatus = (String)hashMap.get("equipmentStatus");
        String lastMaintenanceStartDate = (String)hashMap.get("lastMaintenanceStartDate");
        String lastMaintenanceEndDate = (String)hashMap.get("lastMaintenanceEndDate");

        User user = (User) session.getAttribute("user");

        if (user != null) {
            // 创建一个Map对象来存储查询参数
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("equipmentName", equipmentName);
            params.put("equipmentStatus", equipmentStatus);
            params.put("lastMaintenanceStartDate", lastMaintenanceStartDate);
            params.put("lastMaintenanceEndDate", lastMaintenanceEndDate);


            Page<PropertyMeterPropertyDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage result = propertyMeterService.getPropertyMetersWithProperty(page, params);

            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            // 用户未登录
            return Result.fail();
        }
    }
}
