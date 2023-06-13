package com.allblue.modules.sys.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

//@Component
public class KeyExpiresMessegeListner implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String itemValue = (String) redisTemplate.getStringSerializer().deserialize(body);
        String itemChannel = (String) redisTemplate.getStringSerializer().deserialize(channel);
        String itemPattern = (String) redisTemplate.getStringSerializer().deserialize(pattern);
        System.out.println(itemChannel);
        System.out.println(itemValue);
        System.out.println(itemPattern);
    }
}
