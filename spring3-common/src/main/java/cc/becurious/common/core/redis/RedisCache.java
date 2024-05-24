package cc.becurious.common.core.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"unchecked","rawtypes"})
@Component
public class RedisCache {

    @Resource
    private RedisTemplate redisTemplate;


    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }


    public Object getCacheObject(String tokenKey) {
        return redisTemplate.opsForValue().get(tokenKey);
    }
}
