package com.zj.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    //@RequestMapping(value="/user/login",method = RequestMethod.POST)
    @PostMapping("/user/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        Map<String, Object> map, HttpSession session) {
        if (StringUtils.isNotBlank(userName) && "123456".equals(password)) {
            //登陆成功，防止表单重复提交，使用重定向
            session.setAttribute("loginUser", userName);
            return "redirect:/main.html";
        } else {
            map.put("msg", "用户名或密码错误！");
            return "login";
        }
    }
}
