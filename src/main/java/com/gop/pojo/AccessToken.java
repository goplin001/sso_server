/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class AccessToken {
    private Integer id;
    private String accessToken;
    private Integer userId;
    private String userName;
    private String ip;
    private Integer clientId;
    private String channel;
    private Long expiresIn;
    private Integer createUser;
    private Date createTime;
    private Integer updateUser;
    private Date updateTime;
}