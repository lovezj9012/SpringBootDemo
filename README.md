### 一、配置文件

#### 1、@value获取值和@ConfigurationProperties获取值比较

松散语法，firstName、first-name可以随意写

![](D:\workspace2020\SpringBootDemo\picture\1.jpg)

|                     | @ConfigurationProperties | @Value         |
| ------------------- | ------------------------ | -------------- |
| 功能                | 批量注入配置文件中的属性 | 需要一个个指定 |
| 松散绑定(松散语法)  | 支持                     | 不支持         |
| SPEl（Sring表达式） | 不支持                   | 支持           |
| JSR303数据校验      | 支持                     | 不支持         |
| 复杂类型封装        | 支持                     | 不支持         |

配置文件yml或properties都可以获取到值

在业务逻辑中只需要获取配置文件中的某个属性值使用@Value

编写一个javaBean与配置文件映射使用@ConfigurationProperties

#### 2、配置文件注入值数据校验

```java
@Component
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
	@Email
	private String lastName;

	@Value("#{11*2}")
	private Integer age;

	private Map<String, Object> maps;

	private List<Object> lists;
	
	private Dog dog;
}
```