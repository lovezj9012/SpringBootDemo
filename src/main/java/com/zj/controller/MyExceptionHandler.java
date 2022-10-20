package com.zj.controller;

import com.zj.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

    //第一种浏览器和客户端都是json
/*    @ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String,Object> handlerException(Exception e){
        Map<String,Object> map =new HashMap<>();
        map.put("code","用户不存在！");
        map.put("message",e.getMessage());

        return map;
    }*/


    @ExceptionHandler(UserNotExistException.class)
    public String handlerException(Exception e, HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        map.put("code","用户不存在！");
        map.put("message",e.getMessage());
        request.setAttribute("javax.servlet.error.status_code",500);
        return "forward:/error";
    }
}
