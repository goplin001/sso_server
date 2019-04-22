/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class RefreshToken {
    private Integer id;
    private Integer tokenId;
    private String refreshToken;
    private Long expiresIn;
    private Integer createUser;
    private Date createTime;
    private Integer updateUser;
    private Date updateTime;
}