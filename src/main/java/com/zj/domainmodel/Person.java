package com.zj.domainmodel;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 将配置文件中配置的每个属性值，银蛇到这个组件中
 * @ConfigurationProperties 告诉SpringBoot将本类中的所有属性和配置文件中的属性进行绑定 
 * prefix = "person" 配置文件中的哪个下面的所有属性进行一一映射
 * 
 * @Component只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties
 * 
 * 单元测试想要获取属性值，不能使用@Data注解
 * 
 * @PropertySource加载指定配置文件
 */

@Component
@PropertySource(value = {"classpath:person.properties"})
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {

	/**
	 * 等同于
	 * <bean class="Person">
	 *  <property name="lastName" value="张三" ${key}从环境变量或配置文件获取值/#{Spring表达式}></property>
	 * </bean>
	 * 
	 * 从application.properties获取值
	 */
	//@Value("${person.lastName}")
	
	//lastName必须符合邮箱格式
	//@Email
	private String lastName;

	//@Value("#{11*2}")
	private Integer age;

	private Map<String, Object> maps;

	private List<Object> lists;
	
	private Dog dog;

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

	

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", maps=" + maps + ", lists=" + lists + ", dog=" + dog
				+ "]";
	}
}
