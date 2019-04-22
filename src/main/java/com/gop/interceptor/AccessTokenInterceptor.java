/**
 * @Author Ljm
 * @Date 2019/4/9
 */
package com.gop.interceptor;

import com.gop.constant.Constants;
import com.gop.pojo.AccessToken;
import com.gop.service.SsoService;
import com.gop.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessTokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private SsoService ssoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String access_token = request.getParameter(Constants.SSO_ACCESS_TOKEN);
        if (StringUtils.isNotBlank(access_token)) {
            //查询数据库中的Access Token
            AccessToken accessToken = ssoService.getByAccessToken(access_token);
            if (accessToken != null) {
                Long savedExpiresAt = accessToken.getExpiresIn();
                //如果Access Token已经失效，则返回错误提示
                return DateUtil.ofEpochSecond(savedExpiresAt, null).isAfter(DateUtil.now());
            }
        }
        return false;
    }
}