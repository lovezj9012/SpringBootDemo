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

#### 3@PropertySource&@ImportResource

**@PropertySource**加载指定配置文件

```java
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
}
```

**@ImportResource** 导入Spring配置文件，让配置文件里的内容生效

SpringBoot里没有的Spring配置文件，我们自己编写的配置文件也不能自动识别

想让Spring配置文件生效，加载进来，**@ImportResource**需要标注在一个配置类上

```java
@ImportResource(locations = {"classpath:bean.xml"})
导入Spring配置文件让其生效
```

SpringBoot推荐给容器中添加组件方式：使用全注解方式

spring 配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean id="helloService" class="com.zj.service.HelloService"></bean>
</beans>
```

1、配置类============》Spring配置文件

2、使用@Bean给容器添加组件

```java
@Configuration
public class MyAppConfig {
	/**
	 * 将方法返回值添加到容器中：容器中这个组件默认id就是方法名
	 * 
	 */
	@Bean
	public HelloService helloService() {
		System.out.println("配置类@Bean给容器中添加组件了。。。。");
		return new HelloService();
	}
}
```

####  4、配置文件占位符

![](D:\workspace2020\SpringBootDemo\picture\3.jpg)

##### 1、随机数

```java
${random.value} ${random.int} ${random.long}
${random.int(10)} ${random.int[1024,65536]}
```

##### 2、占位符获取之前配置的值，没有可以使用：默认值赋值

```java
person.lastName=张三${random.uuid}
person.age=${random.int}
person.birth=2017/12/12
person.maps.k1=v1
person.maps.k2=10
person.lists=a,d,c
person.dog.name=${person.hello:helloworld}_dog2
person.dog.age=2
```

####  5 、Profile

![](D:\workspace2020\SpringBootDemo\picture\4.jpg)

##### 1、多profile文件

在主文件编写的时候，文件名可以是application-{profile}.properties或者yml；默认使用application.properties或者yml

##### 2、yml支持多文档块方式

```yaml
spring:
  profiles:
    active: dev
   
---
server:
  port: 8084
spring:
  profiles: dev
  
  
---
server:
  port: 8085
spring:
  profiles: prod  #指定属于哪个环境
```



##### 3、激活指定profile

##### 1、在配置文件中指定 spring.profiles.active=dev

##### 2、命令行

​		--spring.profiles.active=dev

​        可以直接在测试的时候，配置参入命令行参数

​        java -jar jar包名称 --spring.profiles.active=dev



##### 3、虚拟机参数

​      -Dspring.profiles.active=dev

#### 6、 配置文件加载位置

![](D:\workspace2020\SpringBootDemo\picture\5.jpg)



文件加载顺序

- -file:/config/(项目根目录下的config)>file:./(项目根目录)>-classpath:/config/>classpath:/

我们还可以通过spring.config.location来改变默认位置

项目打包好后，我们可以使用命令行参数形式，启动项目来指定配置文件的新位置；指定配置文件和默认加载的这些配置文件共同起作用，形成互补配置

#### 7、外部配置加载顺序

![](D:\workspace2020\SpringBootDemo\picture\6.jpg)

server.context-path=boot设置后访问路径需要多加/boot

例如没有加这个配置访问路径是localhost:8080/hello

加了后访问路径是localhost:8080/boot/hello

##### 1.命令行参数

java -jar jar包名 --server.port=8082 --server.context-path=boot

多个配置用空格分开;--配置项=值

##### 2.来自java：comp/envDe JNDI属性

##### 3.java系统属性（System.getProperties()）

##### 4.操作系统环境变量

##### 5.RandomValuePropertySource配置的random.*属性值

有jar包外向jar包内进行寻找；优先加载带profiel

##### 6.jar包外部的application-{profile}.properties或application.yml(带spring.profile)配置文件

##### 7.jar包内部的application-{profile}.properties或application.yml(带spring.profile)配置文件

再来加载不带profile

##### 8.jar包外部的application.properties或application.yml(不带spring.profile)配置文件

##### 9.jar包内部的application.properties或application.yml(不带spring.profile)配置文件

##### 10.@Configuration注解类上的@PropertySource

##### 11.通过SpringApplication.setDefaultProperties指定的默认属性

