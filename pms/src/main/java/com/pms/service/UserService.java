package com.pms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pms
 * @since 2023-11-26
 */
public interface UserService extends IService<User> {

    IPage pageC(Page<User> page);

    IPage pageCC(Page<User> page, Wrapper wrapper);
}
