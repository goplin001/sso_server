/**
 * @Author Ljm
 * @Date 2019/4/9
 */
package com.gop.pojo.model;

import lombok.Data;

@Data
public class SsoModel {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private Integer clientId;
    private Long expiresIn;
}