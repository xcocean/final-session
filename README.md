# final-session

**final-session** 一个轻量级分布式session框架，它可以无限水平扩展你的集群。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
轻巧、易于配置、低入侵是 `final-session` 的设计理念，她轻盈而美。


![core](https://gitee.com/lingkang_top/final-session/raw/master/document/core.png)
<br>
<br>
> 支持redis、数据库存储会话session，推荐使用redis存储方案。通过自定义生成不同集群ID，读写访问不同的redis集群，从而实现节点无限扩展，架构图如下：

![集群架构图](https://gitee.com/lingkang_top/final-session/raw/master/document/%E9%9B%86%E7%BE%A4%E6%9E%B6%E6%9E%84.png)

## 快速入门
在Maven中：
```xml
<dependency>
    <groupId>top.lingkang</groupId>
    <artifactId>final-session-core</artifactId>
    <version>2.0.1</version>
</dependency>
```

#### 在spring中配置：
继承`FinalSessionConfigurerAdapter`类进行配置。<br>
```java
@Order(-19951219)
@Component
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 对项目进行配置
    }
}
```
`提示：必须在所有过滤器的前`<br>
启动项目！

#### 在传统 Servlet 3.1+ 配置
```java
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 对项目进行配置
    }
}
```
然后在web.xml中配置：`提示：必须在所有过滤器的前`<br>
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
启动项目！

## 其他配置说明
### 会话名称和会话ID
默认的cookie为fs，会话ID为UUID，可以定制
```java
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 自定义会话时长 单位：毫秒
        properties.setMaxValidTime(19951219L);
        // 自定义cookie名称
        properties.setCookieName("lingkang");
        // 配置id生成规则
        properties.setIdGenerate(new IdGenerate() {
            @Override
            public String generateId(HttpServletRequest request) {
            // 自定义id的值，可以根据不同id前缀访问不同redis集群，从而实现集群无限扩展
            return UUID.randomUUID().toString();
            }
        });

        // 更多配置 ...
    }
```

### 自定义会话ID的获取方式（适用前后端分离）
当我们使用 `单体应用+前后端分离` 时，为识别当前会话是否登录，可自定义ID获取方式。默认的ID获取方式是通过cookie: `FinalSessionIdCookie`
<br>
我们可以实现 `FinalSessionId` 接口已达到能同时从请求头、请求参数、cookie中获取sessionID。
```java
@Order(Integer.MIN_VALUE - 1995)
@Component
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 自定义ID的获取、设置方式
        properties.setSessionId(new FinalSessionId() {
            @Override
            public String getSessionId(HttpServletRequest request, FinalSessionProperties properties) {
                // 获取会话id的方式，可以通过 请求头、请求参数、cookie中获取
                return request.getHeader("token");// 这只是一个demo
            }

            @Override
            public void setSessionId(HttpServletRequest request, HttpServletResponse response, FinalSessionProperties properties, String sessionId) {
                // 前后端分离，不设置会话ID到session
            }
        });
    }
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
目前支持了默认的mysql`表名必须fs_session`，开发中使用数据库不失是一种智慧！
```sql
CREATE TABLE `fs_session`
(
    `id`          varchar(64) NOT NULL,
    `session`     longblob,
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
`final-session` 底部依赖了spring-boot-jdbc，需要手动添加依赖
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
若以上默认的存储方案不满意，可自行扩展`FinalRepository`接口进行自定义。final-session准备了许多可扩展接口，你可以尽情发挥你的创新想法！

## ~~打包注意事项~~
你的项目使用spring-boot插件进行打包时，不会囊括`system`作用域的依赖，需要配置一下你项目的spring-boot插件
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>repackage</id>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
### 其他
有问题issues、PR，也可以邮箱：**ling-kang@qq.com**
<br><br>也能打赏支持我：
<br>
![pay](https://gitee.com/lingkang_top/final-session/raw/master/document/pay.png)
<br><br>

