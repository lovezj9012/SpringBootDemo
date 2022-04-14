package com.zj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.zj.domainmodel.Person;

/**
 * 
 * springboot单元测试
 *
 *
 * 可以测试期间很方便的类似编码一样进行自动注入
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootAppServer.class)
public class SpringBootConfigTest {
	
	@Autowired
	Person person;
	
	@Autowired
	ApplicationContext ioc;
	//记录器
	Logger logger =LoggerFactory.getLogger(getClass());
	@Test
	public void contextLoads() {
		//日志级别
		//由低到高 trance<debug<info<warn<error
		//可以调整输出日志级别；日志只会在这个级别以后的高级别生效
		logger.trace("这个是trace日志。。。");
		logger.debug("这个是debug日志。。。");
		//SpringBoot默认使用info级别，没有指定就使用SpringBoot默认级别：root级别
		logger.info("这个是info日志。。。");
		logger.warn("这个是warn日志。。。");
		logger.error("这个是error日志。。。");
	}
	
	@Test
	public void testHelloService() {
		boolean result = ioc.containsBean("helloService");
		System.out.println(result);
	}
	
	@Test
	public void test() {
		System.out.println(person);
	}
}
