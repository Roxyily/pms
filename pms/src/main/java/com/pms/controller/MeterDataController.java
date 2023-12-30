package com.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.EquipmentPropertyDTO;
import com.pms.entity.MeterData;
import com.pms.entity.Resident;
import com.pms.service.MeterDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/meter-data")
public class MeterDataController {

    @Autowired
    private MeterDataService meterDataService;

    @ApiOperation(value = "查询该仪表的所有数值", notes = "")
    @GetMapping("/listByMeterId/{id}")
    public Result listByMeterId(@PathVariable Integer id) {
        if (id == null) {
            return Result.fail("ID cannot be null");
        }

        LambdaQueryWrapper<MeterData> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(MeterData::getMeterId, id);

        List<MeterData> results = meterDataService.list(lambdaQueryWrapper);
        return results.isEmpty()?Result.fail():Result.suc(results, (long) results.size());
    }
}
