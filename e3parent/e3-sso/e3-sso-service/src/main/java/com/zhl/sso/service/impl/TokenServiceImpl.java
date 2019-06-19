package com.zhl.sso.service.impl;

import com.zhl.pojo.TbUser;
import com.zhl.redis.JedisClient;
import com.zhl.sso.service.TokenService;
import com.zhl.utils.E3Result;
import com.zhl.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.server.SessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 根据token取用户信息
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:" + token);
        // 取不到用户信息，登录已经过期，返回登录
        if (StringUtils.isBlank(json)){
            return  E3Result.build(201,"用户登录已经过期");
        }
        // 取到用户信息,说明用户已经登录,更新token的过期时间
        jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
        // 返回结果，E3result其中包含TbUser
        TbUser user = JsonUtils.jsonToPojo(json,TbUser.class);
        return E3Result.ok(user);
    }
}
