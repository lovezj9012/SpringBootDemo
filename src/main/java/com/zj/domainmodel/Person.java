package com.zj.domainmodel;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 将配置文件中配置的每个属性值，银蛇到这个组件中
 * @ConfigurationProperties 告诉SpringBoot将本类中的所有属性和配置文件中的属性进行绑定 
 * prefix = "person" 配置文件中的哪个下面的所有属性进行一一映射
 * 
 * @Component只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties
 * 
 * 单元测试想要获取属性值，不能使用@Data注解
 */

@Component
@ConfigurationProperties(prefix = "person")
public class Person {

	private String lastName;

	private Integer age;

	private Map<String, Object> maps;

	private List<Object> lists;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Map<String, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}

	public List<Object> getLists() {
		return lists;
	}

	public void setLists(List<Object> lists) {
		this.lists = lists;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}

	private Dog dog;

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", maps=" + maps + ", lists=" + lists + ", dog=" + dog
				+ "]";
	}
}
