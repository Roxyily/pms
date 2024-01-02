package com.pms.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
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
import com.pms.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
@RequestMapping("/property-meter")
public class PropertyMeterController {
    @Autowired
    private PropertyMeterService propertyMeterService;

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private MeterDataService meterDataService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询仪表信息", notes = "在验证用户登录状态后，根据查询参数进行分页查询仪表信息")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpServletRequest request) {
        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }

        // 用户未登录
        if(userId == null) {
            return Result.fail("User is not logged in");
        }

        User user = userService.getById(Integer.valueOf(userId));
        HashMap hashMap = query.getParam();
        String equipmentName = (String) hashMap.get("equipmentName");
        String equipmentStatus = (String)hashMap.get("equipmentStatus");
        String lastMaintenanceStartDate = (String)hashMap.get("lastMaintenanceStartDate");
        String lastMaintenanceEndDate = (String)hashMap.get("lastMaintenanceEndDate");

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
    }

    @ApiOperation(value = "新增物业仪表信息", notes = "在验证用户登录状态后，新增物业仪表信息")
    @PostMapping("/save")
    public Result save(@RequestBody PropertyMeter propertyMeter, HttpServletRequest request) {
        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }

        // 用户未登录
        if(userId == null) {
            return Result.fail("User is not logged in");
        }

        User user = userService.getById(Integer.valueOf(userId));
        // 根据communityId查询property表以获取物业主键
        Integer propertyId = propertyController.getPropertyIdByCommunityId(user.getCommunityId());

        // 将物业主键赋值给propertyMeter对象
        propertyMeter.setPropertyId(propertyId);

        // 保存propertyMeter对象
        boolean save = propertyMeterService.save(propertyMeter);

        return save ? Result.suc() : Result.fail();
    }

    @Transactional
    @ApiOperation(value = "删除物业仪表信息", notes = "根据提供的物业仪表ID删除对应的物业仪表信息以及相关的数据信息")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        // 先删除meter_data表中的相关记录
        LambdaQueryWrapper<MeterData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MeterData::getMeterId, id);
        meterDataService.remove(lambdaQueryWrapper);

        // 然后删除property_meter表中的记录
        return propertyMeterService.removeById(id);
    }

    @ApiOperation(value = "更新物业仪表信息", notes = "在验证用户登录状态后，根据提供的物业仪表信息更新对应的记录")
    @PostMapping("/update")
    public Result update(@RequestBody PropertyMeter propertyMeter, HttpServletRequest request) {
        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }

        // 用户未登录
        if(userId == null) {
            return Result.fail("User is not logged in");
        }

        return propertyMeterService.updateById(propertyMeter)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "导出分页查询的仪表信息到Excel文件", notes = "在验证用户登录状态后，根据查询参数进行分页查询仪表信息，并将数据导出到Excel文件")
    @PostMapping("/exportPage")
    public void exportPage(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String equipmentName = (String)param.get("equipmentName");
        String equipmentStatus = (String)param.get("equipmentStatus");
        String lastMaintenanceStartDate = (String)param.get("lastMaintenanceStartDate");
        String lastMaintenanceEndDate = (String)param.get("lastMaintenanceEndDate");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<PropertyMeterPropertyDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("equipmentName", equipmentName);
            params.put("equipmentStatus", equipmentStatus);
            params.put("lastMaintenanceStartDate", lastMaintenanceStartDate);
            params.put("lastMaintenanceEndDate", lastMaintenanceEndDate);

            Page<PropertyMeterPropertyDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage<PropertyMeterPropertyDTO> result = propertyMeterService.getPropertyMetersWithProperty(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("propertyMeters.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PropertyMeterPropertyDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }

    @ApiOperation(value = "导出所有仪表信息到Excel文件", notes = "在验证用户登录状态后，查询所有满足条件的仪表信息，并将数据导出到Excel文件")
    @PostMapping("/exportAll")
    public void exportAll(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String equipmentName = (String) param.get("equipmentName");
        String equipmentStatus = (String) param.get("equipmentStatus");
        String lastMaintenanceStartDate = (String) param.get("lastMaintenanceStartDate");
        String lastMaintenanceEndDate = (String) param.get("lastMaintenanceEndDate");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<PropertyMeterPropertyDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("equipmentName", equipmentName);
            params.put("equipmentStatus", equipmentStatus);
            params.put("lastMaintenanceStartDate", lastMaintenanceStartDate);
            params.put("lastMaintenanceEndDate", lastMaintenanceEndDate);

            // 创建一个大的分页对象，以便获取所有的记录
            Page<PropertyMeterPropertyDTO> page = new Page();
            page.setCurrent(1);
            page.setSize(Integer.MAX_VALUE);

            IPage<PropertyMeterPropertyDTO> result = propertyMeterService.getPropertyMetersWithProperty(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("propertyMeters.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PropertyMeterPropertyDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }
}
