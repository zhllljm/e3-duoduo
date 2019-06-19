package com.zhl.sso.service;

import com.zhl.pojo.TbUser;
import com.zhl.utils.E3Result;

/**
 * 根据token查询用户信息
 */
public interface TokenService {

    E3Result getUserByToken(String token);
}
