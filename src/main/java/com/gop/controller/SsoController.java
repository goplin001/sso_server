/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.controller;

import com.gop.pojo.User;
import com.gop.service.SsoService;
import com.gop.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("sso")
public class SsoController {
    private final SsoService ssoService;

    @Autowired
    public SsoController(SsoService ssoService) {
        this.ssoService = ssoService;
    }

    //获取Access Token
    @RequestMapping("token")
    public ModelAndView token(@RequestParam String redirect_url, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String ip = RequestUtil.getRequestIp(request);
        String accessToken = ssoService.token(user, ip, redirect_url);
        Map<String, String> map = new HashMap<>();
        map.put("code", accessToken);
        ModelAndView mv = new ModelAndView();
        mv.setView(new RedirectView(redirect_url, true, false));
        mv.getModelMap().addAllAttributes(map);
        return mv;
    }

    //校验Access Token
    @RequestMapping("validate")
    @ResponseBody
    public Map<String, Object> validate(@RequestParam String access_token) {
        return ssoService.validate(access_token);
    }

    //通过Refresh Token刷新Access Token
    @RequestMapping("refreshToken")
    @ResponseBody
    public Map<String, Object> refreshToken(@RequestParam String refresh_token, HttpServletRequest request) {
        String ip = RequestUtil.getRequestIp(request);
        return ssoService.refreshToken(refresh_token, ip);
    }
}