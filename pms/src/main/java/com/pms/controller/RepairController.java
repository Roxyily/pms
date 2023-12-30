package com.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.RepairResidentDTO;
import com.pms.entity.Repair;
import com.pms.entity.Resident;
import com.pms.entity.User;
import com.pms.service.RepairService;
import com.pms.service.ResidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @ApiOperation(value = "查询分页", notes = "")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpSession session) {
        HashMap hashMap = query.getParam();
        String name = (String) hashMap.get("name");
        String contact = (String) hashMap.get("contact");
        String status = (String)hashMap.get("status");
        String startDate = (String)hashMap.get("startDate");
        String endDate = (String)hashMap.get("endDate");
        String keyword = (String)hashMap.get("keyword");

        User user = (User) session.getAttribute("user");

        if (user != null) {
            // 创建一个Map对象来存储查询参数
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", user.getCommunityId());
            params.put("name", name);
            params.put("contact", contact);
            params.put("status", status);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("keyword", keyword);


            Page<RepairResidentDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage result = repairService.getRepairsWithResidents(page, params);

            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            // 用户未登录
            return Result.fail();
        }
    }

    @ApiOperation(value = "删除", notes = "根据repairId删除单条记录")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return repairService.removeById(id);
    }

    @ApiOperation(value = "更新", notes = "根据repairId更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody Repair repair, HttpSession session) {
        System.out.println(repair.getDescription());
        return repairService.updateById(repair)?Result.suc():Result.fail();
    }

}
