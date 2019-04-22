/*     
 *
 * @Author Ljm 
 * @Date 2019/4/8
 *
 */
package com.gop.config;

import com.gop.interceptor.AccessTokenInterceptor;
import com.gop.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptorAdapter userInterceptor() {
        return new UserInterceptor();
    }

    @Bean
    public HandlerInterceptorAdapter accessTokenInterceptor() {
        return new AccessTokenInterceptor();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FormHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns("/sso/token/**");
        registry.addInterceptor(accessTokenInterceptor()).addPathPatterns("/sso/validate/**");
    }
}
