package com.zj.domainmodel;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 将配置文件中配置的每个属性值，银蛇到这个组件中
 * @ConfigurationProperties 告诉SpringBoot将本类中的所有属性和配置文件中的属性进行绑定 
 * prefix = "person" 配置文件中的哪个下面的所有属性进行一一映射
 * 
 * @Component只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

	private String lastName;

	private Integer age;

	private Map<String, Object> maps;

	private List<Object> lists;

	private Dog dog;

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", maps=" + maps + ", lists=" + lists + ", dog=" + dog
				+ "]";
	}
}
