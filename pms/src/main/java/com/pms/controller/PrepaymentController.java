package com.pms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.common.QueryPageParam;
import com.pms.common.Result;
import com.pms.dto.PrepaymentResidentDTO;
import com.pms.entity.Prepayment;
import com.pms.entity.User;
import com.pms.service.PrepaymentService;
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
@RequestMapping("/prepayment")
public class PrepaymentController {

    @Autowired
    private PrepaymentService prepaymentService;

    @ApiOperation(value = "删除", notes = "根据Id删除单条记录")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return prepaymentService.removeById(id);
    }

    @ApiOperation(value = "更新", notes = "根据Id更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody Prepayment prepayment, HttpSession session) {
        System.out.println(prepayment.getDate());
        return prepaymentService.updateById(prepayment)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "查询分页", notes = "")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, Integer communityId) {
        HashMap hashMap = query.getParam();
        String residentName = (String) hashMap.get("residentName");
        String contact = (String) hashMap.get("contact");
        String amount = (String)hashMap.get("amount");
        String prepaymentDate = (String)hashMap.get("prepaymentDate");


        //User user = (User) session.getAttribute("user");

        if (communityId != null) {
            // 创建一个Map对象来存储查询参数
            Map<String, Object> params = new HashMap<>();
            params.put("communityId", communityId);
            params.put("residentName", residentName);
            params.put("contact", contact);
            params.put("amount", amount);
            params.put("prepaymentDate", prepaymentDate);


            Page<PrepaymentResidentDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage result = prepaymentService.getPrepaymentsWithResident(page, params);

            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            // 用户未登录
            return Result.fail();
        }
    }
}
