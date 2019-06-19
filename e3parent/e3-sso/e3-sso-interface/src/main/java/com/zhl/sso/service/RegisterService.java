package com.zhl.sso.service;

import com.zhl.pojo.TbUser;
import com.zhl.utils.E3Result;

public interface RegisterService {
    /**
     * 检查数据是否可用
     *
     * @return
     */
    E3Result checkData(String param, int type);

    /**
     * 注册
     *
     * @param tbUser
     * @return
     */
    E3Result register(TbUser tbUser);
}
