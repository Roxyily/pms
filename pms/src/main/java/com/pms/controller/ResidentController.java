package com.pms.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.entity.Resident;
import com.pms.entity.User;
import com.pms.service.ResidentService;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pms
 * @since 2023-11-26
 */
@RestController
@RequestMapping("/resident")
@CrossOrigin
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询住户信息", notes = "在验证用户登录状态后，根据查询参数进行分页查询住户信息")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpServletRequest request) {
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String unitNumber = (String)param.get("unitNumber");
        String roomNumber = (String)param.get("roomNumber");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) { // 修改这里
                userId = cookie.getValue();
                break;
            }
        }

        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Resident::getCommunityId, user.getCommunityId())
                    .like(Resident::getName, name)
                    .like(Resident::getUnitNumber, unitNumber)
                    .like(Resident::getRoomNumber, roomNumber);

            Page<Resident> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage result = residentService.pageCC(page, lambdaQueryWrapper);

            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            // 用户未登录
            return Result.fail();
        }
    }

    @ApiOperation(value = "新增住户信息", notes = "管理员在验证用户登录状态后，新增小区内的住户信息")
    @PostMapping("/save")
    public Result save(@RequestBody Resident resident, HttpServletRequest request) {
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
        LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Resident::getCommunityId, user.getCommunityId())
                .eq(Resident::getUnitNumber, resident.getUnitNumber())
                .eq(Resident::getRoomNumber, resident.getRoomNumber());
        List<Resident> residents = residentService.list(lambdaQueryWrapper);
        //如果房间重复
        if(!residents.isEmpty()) {
            return Result.fail("Data already exists");
        }

        resident.setCommunityId(user.getCommunityId());
        return residentService.save(resident)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "更新住户信息", notes = "在验证用户登录状态后，更新小区内的住户信息")
    @PostMapping("/update")
    public Result update(@RequestBody Resident resident, HttpServletRequest request) {
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
        LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Resident::getCommunityId, user.getCommunityId())
                .eq(Resident::getUnitNumber, resident.getUnitNumber())
                .eq(Resident::getRoomNumber, resident.getRoomNumber())
                .ne(Resident::getId, resident.getId());  // 添加这行代码来排除当前的居民
        List<Resident> residents = residentService.list(lambdaQueryWrapper);
        //如果房间重复
        if(!residents.isEmpty()) {
            return Result.fail("Data already exists");
        }

        resident.setCommunityId(user.getCommunityId());
        return residentService.updateById(resident)?Result.suc():Result.fail();
    }


    @ApiOperation(value = "删除住户信息", notes = "管理员根据提供的住户ID删除小区内的住户信息")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return residentService.removeById(id);
    }

    @ApiOperation(value = "导出分页查询的居民数据到Excel文件", notes = "在验证用户登录状态后，根据查询参数进行分页查询居民数据，并将数据导出到Excel文件")
    @GetMapping("/export")
    public void export(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String unitNumber = (String)param.get("unitNumber");
        String roomNumber = (String)param.get("roomNumber");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<Resident> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Resident::getCommunityId, user.getCommunityId())
                    .like(Resident::getName, name)
                    .like(Resident::getUnitNumber, unitNumber)
                    .like(Resident::getRoomNumber, roomNumber);

            Page<Resident> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage result = residentService.pageCC(page, lambdaQueryWrapper);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("residents.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), Resident.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }

    @ApiOperation(value = "导出所有居民数据到Excel文件", notes = "在验证用户登录状态后，查询所有满足条件的居民数据，并将数据导出到Excel文件")
    @PostMapping("/exportAll")
    public void exportAll(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String unitNumber = (String)param.get("unitNumber");
        String roomNumber = (String)param.get("roomNumber");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<Resident> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Resident::getCommunityId, user.getCommunityId())
                    .like(Resident::getName, name)
                    .like(Resident::getUnitNumber, unitNumber)
                    .like(Resident::getRoomNumber, roomNumber);

            dataList = residentService.list(lambdaQueryWrapper);
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("residents.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), Resident.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }

    //根据单元号和房间号查询住户主键
    public Integer getResidentIdByUnitAndRoom(String unitNumber, String roomNumber) {
        // 创建查询条件
        LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Resident::getUnitNumber, unitNumber);
        lambdaQueryWrapper.eq(Resident::getRoomNumber, roomNumber);

        // 执行查询
        Resident resident = residentService.getOne(lambdaQueryWrapper);

        // 如果没有找到对应的住户，返回null
        if (resident == null) {
            return null;
        }

        // 返回住户主键
        return resident.getId();
    }
}
