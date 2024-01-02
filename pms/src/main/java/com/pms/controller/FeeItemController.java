package com.pms.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.EquipmentPropertyDTO;
import com.pms.dto.FeeItemPropertyDTO;
import com.pms.entity.Equipment;
import com.pms.entity.FeeItem;
import com.pms.entity.User;
import com.pms.service.EquipmentService;
import com.pms.service.FeeItemService;
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
@RequestMapping("/fee-item")
public class FeeItemController {

    @Autowired
    private FeeItemService feeItemService;

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询收费项目信息", notes = "在验证用户登录状态后，根据查询参数进行分页查询收费项目信息")
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
        String feeItemName = (String) hashMap.get("feeItemName");

        // 创建一个Map对象来存储查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("communityId", user.getCommunityId());
        params.put("feeItemName", feeItemName);

        Page<FeeItemPropertyDTO> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = feeItemService.getFeeItemsWithProperty(page, params);

        return Result.suc(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "新增收费项目", notes = "在验证用户登录状态后，新增收费项目的接口")
    @PostMapping("/saveFeeItem")
    public Result saveFeeItem(@RequestBody FeeItem feeItem, HttpServletRequest request) {
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

        // 将物业主键赋值给feeItem对象
        feeItem.setPropertyId(propertyId);

        // 保存feeItem对象
        boolean save = feeItemService.save(feeItem);

        return save ? Result.suc() : Result.fail();
    }

    @ApiOperation(value = "删除收费项目信息", notes = "根据提供的收费项目ID删除对应的收费项目信息")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return feeItemService.removeById(id);
    }

    @ApiOperation(value = "更新费用项", notes = "根据费用项的Id更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody FeeItem feeItem) {
        System.out.println(feeItem);
        return feeItemService.updateById(feeItem) ? Result.suc() : Result.fail();
    }

    @ApiOperation(value = "导出分页查询的收费项目信息到Excel文件", notes = "在验证用户登录状态后，根据查询参数进行分页查询收费项目信息，并将数据导出到Excel文件")
    @PostMapping("/exportPage")
    public void exportPage(@RequestBody QueryPageParam query, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap param = query.getParam();
        String feeItemName = (String)param.get("feeItemName");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<FeeItemPropertyDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("feeItemName", feeItemName);

            Page<FeeItemPropertyDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage<FeeItemPropertyDTO> result = feeItemService.getFeeItemsWithProperty(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("feeItems.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), FeeItemPropertyDTO.class).build();

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
        String feeItemName = (String)param.get("feeItemName");

        // 从请求中获取Cookie
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        List<FeeItemPropertyDTO> dataList = new ArrayList<>();
        if (userId != null) {
            // 用户已登录，返回用户信息
            User user = userService.getById(Integer.valueOf(userId));
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("feeItemName", feeItemName);

            // 创建一个大的分页对象，以便获取所有的记录
            Page<FeeItemPropertyDTO> page = new Page();
            page.setCurrent(1);
            page.setSize(Integer.MAX_VALUE);

            IPage<FeeItemPropertyDTO> result = feeItemService.getFeeItemsWithProperty(page, params);
            dataList = result.getRecords();
        }

        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("feeItems.xlsx", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

        // 创建一个ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), FeeItemPropertyDTO.class).build();

        // 写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }
}
