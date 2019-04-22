/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.utils;

import com.gop.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestUtil {

    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNoneBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static String getRequestUrl(HttpServletRequest request) {
        //当前请求路径
        StringBuffer url = request.getRequestURL();
        //请求参数
        String query = request.getQueryString();
        if (StringUtils.isNotBlank(query)) {
            url.append("?").append(query);
        }
        try {
            return URLEncoder.encode(url.toString(), Constants.CHARSET_U8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}