package com.zhl.sso.service.impl;

import com.zhl.mapper.TbUserMapper;
import com.zhl.pojo.TbUser;
import com.zhl.pojo.TbUserExample;
import com.zhl.sso.service.RegisterService;
import com.zhl.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public E3Result checkData(String param, int type) {
        //根据不同时type生成不同的查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1.用户 2.手机号 3.邮箱
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if (type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return E3Result.build(400, "非法的参数");
        }
        //执行查询
        List<TbUser> list = tbUserMapper.selectByExample(example);
        //判断结果中是否包含数据
        if (list == null || list.size() == 0){
            //如果没有数据返回true
            return E3Result.ok(true);
        }
        //如果有数据返回false
        return  E3Result.ok(false);
    }

    @Override
    public E3Result register(TbUser tbUser) {
        //数据有效性校验
        if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword()) || StringUtils.isBlank(tbUser.getPhone())){
            return  E3Result.build(400,"用户数据不完整，注册失败");
        }
        //1.用户名 2.手机号 3.邮箱
       E3Result result =  checkData(tbUser.getUsername(),1);
        if (!(boolean) result.getData()){
            return  E3Result.build(400,"此用户名已被注册，注册失败");
        }
       result = checkData(tbUser.getPhone(),2);
        if (!(boolean) result.getData()){
            return  E3Result.build(400,"此手机号已被注册，注册失败");
        }
        //补全pojo的属性
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //对密码进行MD5加密
        String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Pass);
        //把用户数据插入到数据库中
        tbUserMapper.insert(tbUser);
        //返回添加成功
        return E3Result.ok();
    }
}
