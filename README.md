# final-session

**final-session** 一个轻量级分布式session框架，它可以无限水平扩展你的集群。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
轻巧、易于配置、低入侵是 `final-session` 的设计理念，轻盈而美。如果眼下还是一团零星之火，那运筹帷幄之后，迎面东风，就是一场烈焰燎原吧，那必定会是一番尽情的燃烧。待，秋风萧瑟时，散作满天星辰，你看那四季轮回 ，正是 final-session 不灭的执念。


![core](https://gitee.com/lingkang_top/final-session/raw/master/document/core.png)
<br>
<br>
支持redis、数据库存储会话session，推荐使用redis存储方案。通过自定义生成不同集群ID，读写访问不同的redis集群，从而实现节点无限扩展，架构图如下：

![集群架构图](https://gitee.com/lingkang_top/final-session/raw/master/document/%E9%9B%86%E7%BE%A4%E6%9E%B6%E6%9E%84.png)

## 快速入门
拉取代码，使用maven打包
```shell
mvn clean package
```
得到项目核心包：`final-session-core/target/final-session-core-1.0.0.jar`<br>
将`final-session-core-1.0.0.jar`引入项目
例如Maven中：
```xml
<dependency>
    <groupId>top.lingkang</groupId>
    <artifactId>final-session-core</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/final-session-core-1.0.0.jar</systemPath>
</dependency>
```
## 配置
### 使用配置
继承`FinalSessionConfigurerAdapter`类进行配置。<br>
在spring中配置：`(必须在所有拦截器的前面)`
```java
@Order(Integer.MIN_VALUE - 1995)
@Component
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 对项目进行配置
    }
}
```
在传统 Servlet 3.1+ 配置
```java
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 对项目进行配置
    }
}
```
然后在web.xml中配置：`(必须在所有拦截器的前面)`
```xml
<filter-mapping>
    <filter-name>sessionFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
<filter>
    <filter-name>sessionFilter</filter-name>
    <filter-class>top.lingkang.exampleservlet.config.MyFinalSessionConfig</filter-class>
</filter>
```
### 会话名称和会话ID
默认的cookie为fs，会话ID为UUID，可以定制
```java
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 自定义会话时长
        properties.setMaxValidTime(19951219L);
        // 自定义cookie名称
        properties.setCookieName("lingkang");
        // 配置id生成规则
        properties.setIdGenerate(new IdGenerate() {
            @Override
            public String generateId() {
            // 自定义id的值，可以根据不同id前缀访问不同redis集群，从而实现集群无限扩展
            return UUID.randomUUID().toString();
            }
        });

        // 更多配置 ...
    }
```

### 会话的存储
通过存储于db、nosql中，实现分布式会话存储。
#### 存储于redis中
添加依赖
```xmnl
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
配置
```java
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 默认会话存储于内存中，下面将会话存储于redis中，需要引入RedisTemplate依赖
        properties.setRepository(new FinalRedisRepository(redisTemplate));
    }
```

#### 存储于数据库
目前支持了mysql(`表名必须fs_session`)，你可以自行扩展接口，支持其他数据库。
```sql
CREATE TABLE `fs_session`
(
    `id`          varchar(64) NOT NULL,
    `session`     blob,
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
```
忽略了数据库连接等，配置如下
```java
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 默认会话存储于内存中，下面将会话存储于mysql中，需要引入JdbcTemplate依赖
        properties.setRepository(new FinalDataBaseRepository(jdbcTemplate));
    }
```
若默认方案不满意，可自行扩展`FinalRepository`接口进行自定义。final-session准备了许多可扩展接口，你可以尽情发挥你的idea。

### 其他
有问题issues，也可以邮箱：**ling-kang@qq.com**
<br><br>也能打赏我：
<br>
![pay](https://gitee.com/lingkang_top/final-session/raw/master/document/pay.png)
<br><br>

