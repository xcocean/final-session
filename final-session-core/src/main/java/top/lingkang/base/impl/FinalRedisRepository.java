package top.lingkang.base.impl;

import org.springframework.data.redis.core.RedisTemplate;
import top.lingkang.base.FinalRepository;
import top.lingkang.config.FinalSessionProperties;
import top.lingkang.wrapper.FinalSession;

import java.util.concurrent.TimeUnit;

/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class FinalRedisRepository implements FinalRepository {

    private RedisTemplate redisTemplate;
    private FinalSessionProperties properties;

    public FinalRedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public FinalSession getSession(String id) {
        Object o = redisTemplate.opsForValue().get(id);
        if (o != null)
            return (FinalSession) o;
        return null;
    }

    @Override
    public void setSession(String id, FinalSession session) {
        redisTemplate.opsForValue().set(id, session, properties.getMaxValidTime(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void setFinalSessionProperties(FinalSessionProperties properties) {
        this.properties = properties;
    }

    @Override
    public void destroy() {

    }
}
