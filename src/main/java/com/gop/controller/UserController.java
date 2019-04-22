/**
 * @Author Ljm
 * @Date 2019/4/9
 */
package com.gop.controller;

import com.gop.constant.Constants;
import com.gop.pojo.User;
import com.gop.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("login")
    public ModelAndView login(@RequestParam String redirect_uri, HttpSession session) {
        session.setAttribute(Constants.SSO_REDIRECT_URI, redirect_uri);
        return new ModelAndView(new RedirectView(Constants.HTML_LOGIN, true, false));
    }

    @PostMapping("doLogin")
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam String username, @RequestParam String pwd, HttpSession session) {
        Map<String, Object> result = new HashMap<>(1);
        User user = userService.doLogin(username, pwd);
        //登录验证通过
        if (user != null) {
            //session中添加用户信息
            session.setAttribute(Constants.SESSION_USER, user);
            //登录成功之后的回调地址
            String redirect_uri = (String) session.getAttribute(Constants.SSO_REDIRECT_URI);
            session.removeAttribute(Constants.SSO_REDIRECT_URI);
            if (StringUtils.isNotBlank(redirect_uri)) {
                result.put("redirect_uri", redirect_uri);
            }
        }
        return result;
    }
}