package com.bzwebframework.web.service.impl;

import com.bzwebframework.web.mapper.UserMapper;
import com.bzwebframework.web.model.entity.User;
import com.bzwebframework.web.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author CaptainBing
 * @Date 2023/11/8 22:24
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
