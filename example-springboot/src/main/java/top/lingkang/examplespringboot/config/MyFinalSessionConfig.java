package top.lingkang.examplespringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.lingkang.base.impl.FinalRedisRepository;
import top.lingkang.config.FinalSessionConfigurerAdapter;
import top.lingkang.config.FinalSessionProperties;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
@Order(-1995)
@Component
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void configurer(FinalSessionProperties properties) {
        properties.setRepository(new FinalRedisRepository(redisTemplate));
    }
}
