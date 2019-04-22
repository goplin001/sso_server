/*     
 *
 * @Author Ljm 
 * @Date 2019/4/8
 *
 */
package com.gop.interceptor;

import com.gop.constant.Constants;
import com.gop.pojo.User;
import com.gop.utils.RequestUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        if (user != null) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + Constants.URL_LOGIN + RequestUtil.getRequestUrl(request));
        return false;
    }
}
