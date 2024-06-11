package com.housekeeping.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    ObjectMapper mapper = new ObjectMapper();

    public void set(String key, Object value) {

        String v = null;
        try {
            v = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (v != null) {
            stringRedisTemplate.opsForValue().set(key, v);
        }
    }

    public void set(String key, Object value, long time) {
        String v = null;
        try {
            v = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (v != null) {
            stringRedisTemplate.opsForValue().set(key, v, time, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        String v = null;
        try {
            v = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (v != null) {
            stringRedisTemplate.opsForValue().set(key, v, time, timeUnit);
        }
    }

    public Object get(String key) {
        String v = stringRedisTemplate.opsForValue().get(key);
        if (v != null) {
            try {
                return mapper.readValue(v, Object.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
