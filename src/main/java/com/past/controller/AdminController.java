package com.past.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理员后台管理
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @RequestMapping("/index.page")
    public String index(){

        return "admin";
    }


}
