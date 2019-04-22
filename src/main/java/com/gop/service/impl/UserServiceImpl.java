/**
 * @Author Ljm
 * @Date 2019/4/9
 */
package com.gop.service.impl;

import com.gop.dao.UserDao;
import com.gop.pojo.User;
import com.gop.service.UserService;
import com.gop.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User doLogin(String username, String pwd) {
        User user = userDao.getByUsername(username);
        boolean flag = EncryptUtil.checkSha256Crypt(pwd, user.getPassword());
        if (flag) {
            return user;
        }
        return null;
    }
}