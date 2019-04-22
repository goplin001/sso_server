/**
 * @Author Ljm
 * @Date 2019/4/9
 */
package com.gop.dao;

import com.gop.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    String FIELD_USER = " id,username,password,mobile,email,create_time,update_time,status ";

    @Select("select" + FIELD_USER + "from user where username = #{username}")
    User getByUsername(String username);

    @Select("select" + FIELD_USER + "from user where id = #{id}")
    User getByPK(Integer id);
}