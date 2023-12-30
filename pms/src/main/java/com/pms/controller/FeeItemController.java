package com.pms.controller;


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
@RequestMapping("/fee-item")
public class FeeItemController {

    @Autowired
    private FeeItemService feeItemService;

    @ApiOperation(value = "删除", notes = "根据Id删除单条记录")
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return feeItemService.removeById(id);
    }

    @ApiOperation(value = "更新", notes = "根据Id更新单条记录")
    @PostMapping("/update")
    public Result update(@RequestBody FeeItem feeItem, HttpSession session) {
        System.out.println(feeItem);
        return feeItemService.updateById(feeItem)?Result.suc():Result.fail();
    }

    @ApiOperation(value = "查询分页", notes = "")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query, HttpSession session) {
        HashMap hashMap = query.getParam();
        String feeItemName = (String) hashMap.get("feeItemName");

        User user = (User) session.getAttribute("user");

        if (user != null) {
            // 创建一个Map对象来存储查询参数
            Map<String, Object> params = new HashMap<>();
            params.put("communityId",  user.getCommunityId());
            params.put("feeItemName", feeItemName);

            Page<FeeItemPropertyDTO> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            IPage result = feeItemService.getFeeItemsWithProperty(page, params);

            return Result.suc(result.getRecords(), result.getTotal());
        } else {
            // 用户未登录
            return Result.fail();
        }
    }

}
