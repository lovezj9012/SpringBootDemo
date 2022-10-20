package com.zj.controller;

import com.zj.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorld {

	@ResponseBody
	@GetMapping("/hello")
	public String Hello(@RequestParam("user") String text) {
		if("111".equals(text)){
			throw new UserNotExistException();
		}
		return "Hello World !";
	}


}
