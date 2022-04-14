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

#### 8、自动配置原理

配置文件能写什么？怎么写？自动配置原理：

[配置文件能配置的属性参照](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/htmlsingle/#appendix)

##### 1、自动配置原理：

1、SpringBoot启动的时候加载主配置类，开启了自动配置功能@EnableAutoConfiguration

2、@EnableAutoConfiguration作用

  - 利用AutoConfigurationImportSelector给容器导入一些组件
  - 可以参照selectImports方法内容
  - List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);该方法获取候选配置
    - 

```java
SpringFactoriesLoader.loadFactoryNames(）
扫描所有jar包类路径下 META-INF/spring.factories
把扫描到这些文件的内容包装成properties对象
从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后把它们添加到容器中
```

将类路径下  META-INF/spring.factories里面配置的所有EnableAutoConfiguration的值加入到容器中

**spring-boot-autoconfigure-2.6.6.jar中META-INF/spring.factories下的自动配置**

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration,\
org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration,\
org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration,\
org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration,\
org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration,\
org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration,\
org.springframework.boot.autoconfigure.netty.NettyAutoConfiguration,\
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration,\
org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration,\
org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration,\
org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration,\
org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration,\
org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration,\
org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration,\
org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration,\
org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration,\
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration,\
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration,\
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration,\
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration,\
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration
```

每一个这样的xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；来用它们来做自动配置

3、每一个自动配置类进行自动配置功能

4、以HttpEncodingAutoConfiguration为例解释自动配置原理

```java
@Configuration(proxyBeanMethods = false) //表示这是一个配置类，类似以前编写的配置文件，也可以给容器添加组件
@EnableConfigurationProperties(ServerProperties.class) //启动指定类ConfigurationProperties功能；将配置文件中对应的值和ServerProperties中的属性绑定起来；并把Encoding加入到ioc容器
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET) //Spring底层@Conditional注解，根据不同的条件，如果满足指定条件，整个配置类的配置就会生效；判断当前应用是否是web应用，如果是，配置生效
@ConditionalOnClass(CharacterEncodingFilter.class) //判断当前项目有没有这个类；CharacterEncodingFilter是SpringMVC中进行乱码解决的过滤器
@ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true) //判断配置文件中是否存在某个配置 server.servlet.encoding.enabled;如果不存在判断也是成立的
//即使我们配置文件中不配置server.servlet.encoding.enabled=true，也是默认生效
public class HttpEncodingAutoConfiguration {
    //它已经和SpringBoot的配置文件映射了
    private final Encoding properties;
    //只有一个有参构造器的情况下，参数值就会从容器中获取
    public HttpEncodingAutoConfiguration(ServerProperties properties) {
		this.properties = properties.getServlet().getEncoding();
	}
    @Bean //给容器中添加一个组件，这个组件的某些值需要从properties中获取
	@ConditionalOnMissingBean //判断容器中没有这个组件
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.properties.getCharset().name());
		filter.setForceRequestEncoding(this.properties.shouldForce(Encoding.Type.REQUEST));
		filter.setForceResponseEncoding(this.properties.shouldForce(Encoding.Type.RESPONSE));
		return filter;
	}
}
```

根据当前不同的条件判断，决定这个配置是否生效

一旦配置类生效，这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中来获取的，这些类里面的每一个属性又是和配置文件绑定的

5、所有在配置文件中能配置的属性都是在xxxxProperties类中封装着；配置文件能配置什么就可以参照某个功能对应的这个属性类

```java
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true) //从配置文件中获取指定的值和bean的属性进行绑定 
public class ServerProperties {
```

精髓

1、SpringBoot启动会加载大量的自动配置类

2、看我们需要的功能有没有SpringBoot默认写好的自动配置类

3、再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）

4、给容器中自动类添加组件，会从properties类中获取某些属性，我们就可以配置文件中指定这些属性的值

##### 2、细节

###### 1、@Conditional派生注解（Srping注解版原生的@Conditional作用）

作用：必须是@Conditional指定条件成立，才给容器添加组件，配置类里所有的内容才生效。

![](E:\Java\workspace\SpringBootDemo\picture\7.jpg)

**自动配置类必须在满足一定条件下才能生效**

怎么判断哪些自动配置类是否生效

可以在properties/yaml配置文件中设置debug=true开始debug模式，让控制台输出自动配置报告

#### 二、日志

#### 1、日志框架

![image-20220413084627356](E:\Java\workspace\SpringBootDemo\picture\8.jpg)

SringBoot：底层是Spring框架，Spring框架默认使用JCL。

**SpringBoot选用的是SLF4j和Logback**

#### 2、SLF4j的使用

##### 1.如何在系统上使用SLF4j

以后开发的时候，日志记录方法调用，不应该直接调用日志实现类，而是直接调用日志抽象类里的方法

在工程中导入slf4j的jar包和logback的jar包

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

每一个日志实现框架都有自己的配置文件，使用slf4j以后，配置文件还是做成日志实现框架的配置文件

##### 2.遗留问题

a(slf4j+logback):Spring(commons -logging) Hibernate(Jboss-logging) MyBatis xxxxx

统一日志记录，即使是别的框架和我一起统一使用slf4j进行输出

![](E:\Java\workspace\SpringBootDemo\picture\9.jpg)

**如何让系统中所有的日志都统一到slf4j**

1.将系统中的其他日志框架先排除出去

2.用中间包来替换原来的日志框架

3.导入slf4j其他的实现

#### 3、SpringBoot日志关系

```xml
 <dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter</artifactId>
	</dependency>
```

SpringBoot使用它来做日志功能

```xml
 <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
 </dependency>
```

1.SpringBoot底层也是使用slf4j+logback的方式进行日志记录

2.SpringBoot也把其他日志都替换成slf4j

3.中间替换包

![image-20220413134209833](E:\Java\workspace\SpringBootDemo\picture\11.jpg)

4.如果引入了其他框架？一定要移除这个框架的默认的日志依赖移除掉

Spring框架用的是commons-logging

![](E:\Java\workspace\SpringBootDemo\picture\10.jpg)

==SpringBoot能自动适配所有的日志，而底层使用slf4j+logback方式记录日志，引入其他框架的时候，只需将这个框架依赖的日志框架排除掉==

#### 4、日志使用

##### 1、默认配置

SpringBoot默认帮我们配置好了日志

![image-20220413140057247](E:\Java\workspace\SpringBootDemo\picture\12.jpg)

![image-20220413144918544](E:\Java\workspace\SpringBootDemo\picture\13.jpg)

##### 2、指定配置

给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用它默认的配置了

![image-20220413171452762](E:\Java\workspace\SpringBootDemo\picture\14.jpg)

logback.xml：直接会被框架识别

logback-spring.xml：日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置，可以使用SpringBoot的高级Profile功能

```xml
<springProfile name="staging">
	<!-- configuration to be enabled when the "staging" profile is active --> 可以指定某段配置只在某个环境下生效
</springProfile>
```

否则

![image-20220413172206930](E:\Java\workspace\SpringBootDemo\picture\15.jpg)

#### 5、切换日志框架

可以按照slf4j的日志适配图，进行相关切换
