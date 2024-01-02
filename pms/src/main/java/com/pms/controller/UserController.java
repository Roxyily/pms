package com.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.entity.User;
import com.pms.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pms
 * @since 2023-11-24
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @ApiOperation(value = "登录", notes = "所有用户登陆的接口，会通过cookie在后端存储用户登陆信息")
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletResponse response) {
        List<User> list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();
        if (list.size() > 0) {
            // 登录成功，将用户ID存储到cookie中
            User loggedInUser = list.get(0);
            Cookie cookie = new Cookie("userId", String.valueOf(loggedInUser.getId()));
            cookie.setMaxAge(7 * 24 * 60 * 60); // 设置cookie有效期为7天
            cookie.setPath("/"); // 设置cookie的路径为网站的根路径
            response.addCookie(cookie);
            return Result.suc(loggedInUser);
        } else {
            return Result.fail();
        }
    }


    //注册用户
    @ApiOperation(value = "注册用户", notes = "小程序端通过该接口注册小区服务平台的账号")
    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody User user, Integer residentId) {
        // 输入验证
        if (user.getName() == null || user.getPassword() == null) {
            return Result.fail("username or password is null!");
        }

        if (user.getNo() == null) {
            return Result.fail("name is null!");
        }

        if(user.getCommunityId() == null) {
            return Result.fail("communityIdData is null!");
        }

        //分配普通用户权限
        user.setRoleId(2);

        //绑定用户和住户信息
        user.setResidentId(residentId);

        boolean save = userService.save(user);
        return save?Result.suc():Result.fail();
    }

    //注册管理员
    @ApiOperation(value = "注册管理员", notes = "PC端通过该接口登录小区物业管理系统，由超级管理员分配管理员账户")
    @PostMapping("/registerAdministrator")
    public Result registerAdministrator(@RequestBody User user) {
        // 输入验证
        if (user.getName() == null || user.getPassword() == null) {
            return Result.fail("username or password is null!");
        }

        if (user.getNo() == null) {
            return Result.fail("name is null!");
        }

        if(user.getCommunityId() == null) {
            return Result.fail("communityIdData is null!");
        }

        //分配管理员权限
        user.setRoleId(1);

        boolean save = userService.save(user);

        return save?Result.suc():Result.fail();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public List<User> listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName, user.getName());

        return userService.list(lambdaQueryWrapper);
    }

    //查询（模糊、匹配）
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query) {

        System.out.println(query);
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);


        IPage result = userService.page(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);


        IPage result = userService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);


        IPage result = userService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}
