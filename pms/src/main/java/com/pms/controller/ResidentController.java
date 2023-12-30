package com.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.entity.Resident;
import com.pms.entity.User;
import com.pms.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    //查询
    @GetMapping("/list")
    public List<Resident> list() {
        return residentService.list();
    }

    //查询（匹配）
    @PostMapping("/listP")
    public Result listP(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 用户已登录，返回用户信息
            LambdaQueryWrapper<Resident> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Resident::getCommunityId, user.getCommunityId());
            return Result.suc(residentService.list(lambdaQueryWrapper));
        } else {
            // 用户未登录
            return Result.fail();
        }
    }
    //查询（分页）
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpSession session) {
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String unitNumber = (String)param.get("unitNumber");
        String roomNumber = (String)param.get("roomNumber");

        User user = (User) session.getAttribute("user");

        if (user != null) {
            // 用户已登录，返回用户信息
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

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Resident resident, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // 用户未登录
        if(user == null) {
            return Result.fail("User is not logged in");
        }

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

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Resident resident, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 用户未登录
        if(user == null) {
            return Result.fail("User is not logged in");
        }
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

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return residentService.removeById(id);
    }
}
