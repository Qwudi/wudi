package com.hwq.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowenqiang
 * @Description:
 */
@Component
public class SimpleLimiter {

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SimpleLimiter(RedisTemplate<String, Object> redisTemplate) {
        SimpleLimiter.redisTemplate = redisTemplate;
    }

    public static boolean isAllowOperation(String key,int time,int maxCount) {
        long l = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(key, l, l);
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, l - time * 1000);
        Long aLong = redisTemplate.opsForZSet().zCard(key);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return aLong <= maxCount;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(isAllowOperation("cccc", 60, 5));
        }
    }
}
