/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public enum ExpireEnum {
    AUTHORIZATION_CODE(10L, 10L * 60, TimeUnit.MINUTES),  //Authorization Code的有效期为10分钟
    ACCESS_TOKEN(30L, 30L * 86400, TimeUnit.DAYS),           //Access Token的有效期为30天
    REFRESH_TOKEN(365L, 365L * 86400, TimeUnit.DAYS)          //Refresh Token的有效期为365天
    ;

    private Long time;          //过期时间
    private Long second;
    private TimeUnit timeUnit;  //时间单位
}