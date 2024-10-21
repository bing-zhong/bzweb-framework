package com.bzwebframework.web.controller;

import com.bzwebframework.web.common.BaseResponse;
import com.bzwebframework.web.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author CaptainBing
 * @Date 2023/11/8 22:10
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get")
    public BaseResponse<String> testGet(){
        return ResultUtils.success("你好");
    }
}
