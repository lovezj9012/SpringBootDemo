package com.zj;

import org.junit.Test;
import org.junit.runner.RunWith;
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
