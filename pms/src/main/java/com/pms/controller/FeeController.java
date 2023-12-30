package com.pms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.FeeResidentItemDTO;
import com.pms.entity.Fee;
import com.pms.entity.User;
import com.pms.service.FeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @ApiOperation(value = "删除", notes = "根据complaintId删除单条记录")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return feeService.removeById(id);
    }

    @ApiOperation(value = "更新", notes = "根据complaintId更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody Fee fee, HttpSession session) {
        System.out.println(fee);
        return feeService.updateById(fee)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "查询分页", notes = "")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpSession session) {
        HashMap hashMap = query.getParam();
        String residentName = (String) hashMap.get("residentName");
        String feeItemName = (String) hashMap.get("feeItemName");
        String status = (String)hashMap.get("status");
        String startDate = (String)hashMap.get("startDate");
        String endDate = (String)hashMap.get("endDate");

        User user = (User) session.getAttribute("user");

        if (user != null) {
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
        } else {
            // 用户未登录
            return Result.fail();
        }
    }
}
