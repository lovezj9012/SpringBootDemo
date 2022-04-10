package com.zj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zj.service.HelloService;

/**
 * @Configuration 指明当前类是一个配置类，作用就是替代之前的Spring配置类
 *
 *                在配置文件中相当于<bean></bean>标签添加组件
 */
@Configuration
public class MyAppConfig {

	/**
	 * 将方法返回值添加到容器中：容器中这个组件默认id就是方法名
	 * 
	 */
	@Bean
	public HelloService helloService() {
		System.out.println("SpringBoot配置类@Bean给容器中添加组件了。。。。");
		return new HelloService();
	}
}
