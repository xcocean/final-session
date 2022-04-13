package top.lingkang.sessioncore.base.impl;

import org.springframework.data.redis.core.RedisTemplate;
import top.lingkang.sessioncore.base.FinalRepository;
import top.lingkang.sessioncore.config.FinalSessionProperties;
import top.lingkang.sessioncore.wrapper.FinalSession;

import javax.servlet.http.HttpServletRequest;
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
    public void deleteSession(String id, HttpServletRequest request) {
        redisTemplate.delete(id);
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
