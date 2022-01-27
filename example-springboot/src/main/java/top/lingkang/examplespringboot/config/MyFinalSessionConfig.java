package top.lingkang.examplespringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.lingkang.sessioncore.base.impl.FinalDataBaseRepository;
import top.lingkang.sessioncore.config.FinalSessionConfigurerAdapter;
import top.lingkang.sessioncore.config.FinalSessionProperties;

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
        //properties.setRepository(new FinalRedisRepository(redisTemplate));
        properties.setRepository(new FinalDataBaseRepository(jdbcTemplate));
    }
}
