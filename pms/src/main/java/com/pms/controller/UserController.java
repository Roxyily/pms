package com.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.entity.User;
import com.pms.service.UserService;
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

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        List list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();
        if (list.size() > 0) {
            // 登录成功，将用户信息存储到session中
            session.setAttribute("user", list.get(0));
            return Result.suc(list.get(0));
        } else {
            return Result.fail();
        }
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
//    public List<User> listPage(@RequestBody HashMap map) {
    public List<User> listPage(@RequestBody QueryPageParam query) {

        System.out.println(query);
        HashMap param = query.getParam();
        String name = (String)param.get("name");
//        System.out.println("name===" + (String)param.get("name"));
//        System.out.println("no===" + (String)param.get("no"));
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.eq(User::getName, user.getName());
//
//        return userService.list(lambdaQueryWrapper);

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
//    public List<User> listPage(@RequestBody HashMap map) {
    public List<User> listPageC(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String)param.get("name");
//        System.out.println("name===" + (String)param.get("name"));
//        System.out.println("no===" + (String)param.get("no"));
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.eq(User::getName, user.getName());
//
//        return userService.list(lambdaQueryWrapper);

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
//    public List<User> listPage(@RequestBody HashMap map) {
    public Result listPageC1(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        String name = (String)param.get("name");
//        System.out.println("name===" + (String)param.get("name"));
//        System.out.println("no===" + (String)param.get("no"));
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
//        lambdaQueryWrapper.eq(User::getName, user.getName());
//
//        return userService.list(lambdaQueryWrapper);

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
