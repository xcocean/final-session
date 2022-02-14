package top.lingkang.examplespringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.lingkang.sessioncore.base.IdGenerate;
import top.lingkang.sessioncore.base.impl.FinalDataBaseRepository;
import top.lingkang.sessioncore.base.impl.FinalRedisRepository;
import top.lingkang.sessioncore.config.FinalSessionConfigurerAdapter;
import top.lingkang.sessioncore.config.FinalSessionProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
@Order(Integer.MIN_VALUE - 1995)
@Component
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 自定义会话时长
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

        // 默认会话存储于内存中，下面将会话存储于mysql中，需要引入JdbcTemplate依赖
        properties.setRepository(new FinalDataBaseRepository(jdbcTemplate));
    }
}
