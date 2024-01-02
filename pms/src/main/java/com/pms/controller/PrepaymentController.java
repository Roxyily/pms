package com.pms.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.PrepaymentResidentDTO;
import com.pms.entity.Prepayment;
import com.pms.entity.User;
import com.pms.service.PrepaymentService;
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
@RequestMapping("/prepayment")
public class PrepaymentController {

    @Autowired
    private PrepaymentService prepaymentService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询预付费信息", notes = "在验证用户登录状态后，根据查询参数进行分页查询预付费信息")
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
        String residentName = (String) hashMap.get("residentName");
        String contact = (String) hashMap.get("contact");
        String amount = (String)hashMap.get("amount");
        String prepaymentDate = (String)hashMap.get("prepaymentDate");

        // 创建一个Map对象来存储查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("communityId", user.getCommunityId());
        params.put("residentName", residentName);
        params.put("contact", contact);
        params.put("amount", amount);
        params.put("prepaymentDate", prepaymentDate);

        Page<PrepaymentResidentDTO> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = prepaymentService.getPrepaymentsWithResident(page, params);

        return Result.suc(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "删除预付费信息", notes = "根据提供的预付费ID删除对应的预付费信息")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return prepaymentService.removeById(id);
    }

    @ApiOperation(value = "更新预付费信息", notes = "根据提供的预付费信息更新对应的记录")
    @PostMapping("/update")
    public Result update(@RequestBody Prepayment prepayment) {
        System.out.println(prepayment.getDate());
        return prepaymentService.updateById(prepayment)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "导出分页查询的预付费信息到Excel文件", notes = "在验证用户登录状态后，根据查询参数进行分页查询预付费信息，并将数据导出到Excel文件")
    @PostMapping("/exportPage")
    public void exportPage(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String residentName = (String)param.get("residentName");
        String contact = (String)param.get("contact");
        String amount = (String)param.get("amount");
        String prepaymentDate = (String)param.get("prepaymentDate");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<PrepaymentResidentDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("residentName", residentName);
            params.put("contact", contact);
            params.put("amount", amount);
            params.put("prepaymentDate", prepaymentDate);

            Page<PrepaymentResidentDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage<PrepaymentResidentDTO> result = prepaymentService.getPrepaymentsWithResident(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("prepayments.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PrepaymentResidentDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }

    @ApiOperation(value = "导出所有预付费信息到Excel文件", notes = "在验证用户登录状态后，查询所有满足条件的预付费信息，并将数据导出到Excel文件")
    @PostMapping("/exportAll")
    public void exportAll(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String residentName = (String)param.get("residentName");
        String contact = (String)param.get("contact");
        String amount = (String)param.get("amount");
        String prepaymentDate = (String)param.get("prepaymentDate");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<PrepaymentResidentDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("residentName", residentName);
            params.put("contact", contact);
            params.put("amount", amount);
            params.put("prepaymentDate", prepaymentDate);

            // 创建一个大的分页对象，以便获取所有的记录
            Page<PrepaymentResidentDTO> page = new Page();
            page.setCurrent(1);
            page.setSize(Integer.MAX_VALUE);

            IPage<PrepaymentResidentDTO> result = prepaymentService.getPrepaymentsWithResident(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("prepayments.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PrepaymentResidentDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }
}
