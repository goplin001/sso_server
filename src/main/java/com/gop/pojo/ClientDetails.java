/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.pojo;

import lombok.Data;

@Data
public class ClientDetails {
    private Integer id;
    private String clientName;
    private String description;
    private String redirectUrl;
    private String logoutUrl;
    private Integer status;
}