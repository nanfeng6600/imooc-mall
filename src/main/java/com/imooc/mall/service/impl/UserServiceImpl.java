package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import com.imooc.mall.util.MD5utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    /**
     * 注册服务
     * @param username 用户名
     * @param password 密码
     * @throws ImoocMallException
     */
    @Override
    public void register(String username, String password) throws ImoocMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(username);
        if (result != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(username);
        try {
            user.setPassword(MD5utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 登录服务
     * @param username
     * @param password
     * @return
     * @throws ImoocMallException
     */
    @Override
    public User login(String username, String password) throws ImoocMallException {
        String md5Password = null;
        try {
            md5Password = MD5utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更新签名
     * @param user
     * @throws ImoocMallException
     */
    @Override
    public void updateInformation(User user) throws ImoocMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }
    @Override
    public boolean checkAdminRole(User user) {
        return user.getRole().equals(2);
    }
}
