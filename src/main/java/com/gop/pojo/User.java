/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private Date createTime;
    private Date updateTime;
    private Integer status;
}