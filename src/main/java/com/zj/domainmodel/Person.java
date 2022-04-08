package com.zj.domainmodel;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Person {

	private String lastName;

	private Integer age;

	private Map<String, Object> maps;

	private List<Object> lists;

	private Dog dog;
}
