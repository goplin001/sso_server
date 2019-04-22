/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.service.impl;

import com.gop.dao.ModelDao;
import com.gop.dao.SsoDao;
import com.gop.dao.UserDao;
import com.gop.enums.ExpireEnum;
import com.gop.pojo.AccessToken;
import com.gop.pojo.ClientDetails;
import com.gop.pojo.RefreshToken;
import com.gop.pojo.User;
import com.gop.pojo.model.SsoModel;
import com.gop.service.SsoService;
import com.gop.utils.DateUtil;
import com.gop.utils.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SsoServiceImpl implements SsoService {
    private final SsoDao ssoDao;
    private final ModelDao modelDao;
    private final UserDao userDao;

    @Autowired
    public SsoServiceImpl(SsoDao ssoDao, ModelDao modelDao, UserDao userDao) {
        this.ssoDao = ssoDao;
        this.modelDao = modelDao;
        this.userDao = userDao;
    }

    @Override
    public String token(User user, String ip, String redirect_uri) {
        //过期时间
        Long expiresIn = ExpireEnum.ACCESS_TOKEN.getSecond();
        //查询接入客户端
        ClientDetails clientDetails = ssoDao.getByRedirectUrI(redirect_uri);
        //生成Access Token
        AccessToken accessToken = saveAccessToken(user.getId(), expiresIn, ip, clientDetails.getId());
        //生成Refresh Token
        RefreshToken refreshToken = saveRefreshToken(user, accessToken);
        return accessToken.getAccessToken();
    }

    @Override
    public Map<String, Object> validate(String access_token) {
        Map<String, Object> result = new HashMap<>(8);
        //过期时间
        Long expiresIn = ExpireEnum.ACCESS_TOKEN.getSecond();
        SsoModel model = modelDao.getModelByAccessToken(access_token);
        if (model == null || StringUtils.isBlank(model.getRefreshToken())) {
            result.put("stat", "0");
            return result;
        }
        //查询用户信息
        User user = userDao.getByPK(model.getUserId());
        if (user == null) {
            result.put("stat", "0");
            return result;
        }
        //组装返回信息
        result.put("stat", "1");
        result.put("access_token", access_token);
        result.put("refresh_token", model.getRefreshToken());
        result.put("expires_in", expiresIn);
        result.put("user", user);
        return result;
    }

    @Override
    public Map<String, Object> refreshToken(String refresh_token, String ip) {
        Map<String, Object> result = new HashMap<>(8);
        SsoModel model = modelDao.getModelByRefresgToken(refresh_token);
        if (model == null) {
            result.put("stat", "0");
            return result;
        }
        //如果Refresh Token已经失效，则需要重新生成
        if (DateUtil.ofEpochSecond(model.getExpiresIn(), null).isBefore(DateUtil.now())) {
            result.put("stat", "0");
            return result;
        }
        //获取对应的用户信息
        User user = userDao.getByPK(model.getUserId());
        if (user == null) {
            result.put("stat", "0");
            return result;
        }
        //生成新的Access Token
        AccessToken accessToken = saveAccessToken(user.getId(), ExpireEnum.ACCESS_TOKEN.getSecond(), ip, model.getClientId());
        //组装返回信息
        result.put("stat", "1");
        result.put("access_token", accessToken.getAccessToken());
        result.put("refresh_token", refresh_token);
        result.put("expires_in", ExpireEnum.ACCESS_TOKEN.getSecond());
        result.put("user", user);
        return result;
    }

    @Override
    public AccessToken getByAccessToken(String access_token) {
        return ssoDao.getByAccessToken(access_token);
    }

    private AccessToken saveAccessToken(Integer userId, Long expiresIn, String ip, Integer clientId) {
        //过期的时间戳
        Long expiresAt = DateUtil.nextDaysSecond(ExpireEnum.ACCESS_TOKEN.getTime(), null);
        //1. 拼装待加密字符串（username + 渠道CODE + 当前精确到毫秒的时间戳）
        String str = userId + clientId + String.valueOf(DateUtil.currentTimeMillis());
        //2. SHA1加密
        String accessTokenStr = "11." + EncryptUtil.sha1Hex(str) + "." + expiresIn + "." + expiresAt;
        //3. 保存Access Token
        AccessToken accessToken = ssoDao.getByUserIdAndClientId(userId, clientId);
        //如果存在匹配的记录，则更新原记录，否则向数据库中插入新记录
        if (accessToken != null) {
            accessToken.setAccessToken(accessTokenStr);
            accessToken.setExpiresIn(expiresAt);
            ssoDao.updateAccessTokenByPK(accessToken);
        } else {
            accessToken = new AccessToken();
            accessToken.setAccessToken(accessTokenStr);
            accessToken.setUserId(userId);
            accessToken.setIp(ip);
            accessToken.setClientId(clientId);
            accessToken.setExpiresIn(expiresAt);
            accessToken.setCreateUser(userId);
            accessToken.setUpdateUser(userId);
            accessToken.setCreateTime(new Date());
            ssoDao.saveAccessToken(accessToken);
        }
        //4. 返回Access Token
        return accessToken;
    }

    private RefreshToken saveRefreshToken(User user, AccessToken accessToken) {
        //过期时间
        Long expiresIn = ExpireEnum.REFRESH_TOKEN.getSecond();
        //过期的时间戳
        Long expiresAt = DateUtil.nextDaysSecond(ExpireEnum.REFRESH_TOKEN.getTime(), null);
        //1. 拼装待加密字符串（username + accessToken + 当前精确到毫秒的时间戳）
        String str = user.getUsername() + accessToken.getAccessToken() + String.valueOf(DateUtil.currentTimeMillis());
        //2. SHA1加密
        String refreshTokenStr = "12." + EncryptUtil.sha1Hex(str) + "." + expiresIn + "." + expiresAt;
        //3. 保存Refresh Token
        RefreshToken refreshToken = ssoDao.getByTokenId(accessToken.getId());
        //如果存在tokenId匹配的记录，则更新原记录，否则向数据库中插入新记录
        if (refreshToken != null) {
            refreshToken.setRefreshToken(refreshTokenStr);
            refreshToken.setExpiresIn(expiresAt);
            ssoDao.updateRefreshTokenByPK(refreshToken);
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setTokenId(accessToken.getId());
            refreshToken.setRefreshToken(refreshTokenStr);
            refreshToken.setExpiresIn(expiresAt);
            refreshToken.setCreateUser(user.getId());
            refreshToken.setUpdateUser(user.getId());
            refreshToken.setCreateTime(new Date());
            ssoDao.saveRefreshToken(refreshToken);
        }
        //4. 返回Refresh Tokens
        return refreshToken;
    }
}