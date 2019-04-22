/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.service;

import com.gop.pojo.AccessToken;
import com.gop.pojo.User;

import java.util.Map;

public interface SsoService {

    String token(User user, String ip, String redirect_uri);

    Map<String, Object> validate(String access_token);

    Map<String, Object> refreshToken(String refresh_token,String ip);

    AccessToken getByAccessToken(String access_token);
}