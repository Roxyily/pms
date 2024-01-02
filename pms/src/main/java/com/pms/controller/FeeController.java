package com.pms.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.FeeResidentItemDTO;
import com.pms.entity.Fee;
import com.pms.entity.User;
import com.pms.service.FeeService;
import com.pms.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @Autowired
    private ResidentController residentController;

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询收费项目分页", notes = "在验证用户登录状态后，根据查询参数进行收费项目的分页查询")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpServletRequest request) {
        HashMap hashMap = query.getParam();
        String residentName = (String) hashMap.get("residentName");
        String feeItemName = (String) hashMap.get("feeItemName");
        String status = (String)hashMap.get("status");
        String startDate = (String)hashMap.get("startDate");
        String endDate = (String)hashMap.get("endDate");

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

        // 创建一个Map对象来存储查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("communityId", user.getCommunityId());
        params.put("residentName", residentName);
        params.put("feeItemName", feeItemName);
        params.put("status", status);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        Page<FeeResidentItemDTO> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = feeService.getFeesWithResidents(page, params);

        return Result.suc(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "新增收费项目", notes = "需要输入Fee的json数据，同时输入单元号和房间号以获取住户主键，通过Cookie获得物业主键")
    @PostMapping("/save")
    public Result save(@RequestBody Fee fee, String unitNumber, String roomNumber, HttpServletRequest request) {
        // 检查fee是否为null
        if (fee == null) {
            return Result.fail("Fee cannot be null");
        }

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String communityId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("communityId")) {
                    communityId = cookie.getValue();
                    break;
                }
            }
        }

        // 用户未登录
        if(communityId == null) {
            return Result.fail("User is not logged in");
        }

        // 根据communityId查询property表以获取物业主键
        Integer propertyId = propertyController.getPropertyIdByCommunityId(Integer.valueOf(communityId));

        // 将物业主键赋值给fee对象
        fee.setPropertyId(propertyId);

        // 根据单元号和房间号查询resident表以获取住户主键
        Integer residentId = residentController.getResidentIdByUnitAndRoom(unitNumber, roomNumber);

        // 将住户主键赋值给fee对象
        fee.setResidentId(residentId);

        // 保存fee对象
        boolean save = feeService.save(fee);

        return save ? Result.suc() : Result.fail();
    }

    @ApiOperation(value = "删除收费项目", notes = "根据收费项目的Id删除单条记录")
    @GetMapping("/deleteFeeItem")
    public boolean deleteFeeItem(Integer id) {
        return feeService.removeById(id);
    }

    @ApiOperation(value = "更新收费项目", notes = "根据收费项目的Id更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody Fee fee, HttpSession session) {
        System.out.println(fee);
        return feeService.updateById(fee) ? Result.suc() : Result.fail();
    }

    @ApiOperation(value = "导出分页查询的收费项目信息到Excel文件", notes = "在验证用户登录状态后，根据查询参数进行分页查询收费项目信息，并将数据导出到Excel文件")
    @PostMapping("/exportPage")
    public void exportPage(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String residentName = (String)param.get("residentName");
        String feeItemName = (String)param.get("feeItemName");
        String status = (String)param.get("status");
        String startDate = (String)param.get("startDate");
        String endDate = (String)param.get("endDate");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<FeeResidentItemDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("residentName", residentName);
            params.put("feeItemName", feeItemName);
            params.put("status", status);
            params.put("startDate", startDate);
            params.put("endDate", endDate);

            Page<FeeResidentItemDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage<FeeResidentItemDTO> result = feeService.getFeesWithResidents(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("feeItems.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), FeeResidentItemDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }

    @ApiOperation(value = "导出所有收费项目信息到Excel文件", notes = "在验证用户登录状态后，查询所有满足条件的收费项目信息，并将数据导出到Excel文件")
    @PostMapping("/exportAll")
    public void exportAll(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String residentName = (String)param.get("residentName");
        String feeItemName = (String)param.get("feeItemName");
        String status = (String)param.get("status");
        String startDate = (String)param.get("startDate");
        String endDate = (String)param.get("endDate");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<FeeResidentItemDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("residentName", residentName);
            params.put("feeItemName", feeItemName);
            params.put("status", status);
            params.put("startDate", startDate);
            params.put("endDate", endDate);

            // 创建一个大的分页对象，以便获取所有的记录
            Page<FeeResidentItemDTO> page = new Page();
            page.setCurrent(1);
            page.setSize(Integer.MAX_VALUE);

            IPage<FeeResidentItemDTO> result = feeService.getFeesWithResidents(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("feeItems.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), FeeResidentItemDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }
}
