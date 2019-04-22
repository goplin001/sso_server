/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.service;

import com.gop.pojo.User;

public interface UserService {

    //登录
    User doLogin(String username, String pwd);
}