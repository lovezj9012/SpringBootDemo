package com.zj.domainmodel;

public class Dog {
	
	@Override
	public String toString() {
		return "Dog [name=" + name + ", age=" + age + "]";
	}

	private String name;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	private Integer age;
}
