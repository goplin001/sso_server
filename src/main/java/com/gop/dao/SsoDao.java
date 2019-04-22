/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop.dao;

import com.gop.pojo.AccessToken;
import com.gop.pojo.ClientDetails;
import com.gop.pojo.RefreshToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SsoDao {
    String FIELD_CLIENT = " id,client_name,description,redirect_url,logout_url,status ";
    String FIELD_REFRESH_TOKEN = " id, token_id, refresh_token, expires_in, create_user, create_time, update_user ";
    String FIELD_REFRESH_TOKEN_VALUE = " #{id}, #{tokenId}, #{refreshToken}, #{expiresIn}, #{createUser}, #{createTime}, #{updateUser} ";
    String FIELD_ACCESS_TOKEN = " id,access_token,user_id,user_name,ip,client_id,channel,expires_in,create_user,create_time,update_user ";
    String FIELD_ACCESS_TOKEN_VALUE = " #{id},#{accessToken},#{userId},#{userName},#{ip},#{clientId},#{channel},#{expiresIn},#{createUser},#{createTime},#{updateUser} ";

    @Select("select" + FIELD_CLIENT + "from sso_client_details where redirect_url = #{redirect_uri}")
    ClientDetails getByRedirectUrI(String redirect_uri);

    @Select("select" + FIELD_ACCESS_TOKEN + "from sso_access_token where user_id = #{user_id} and client_id = #{client_id}")
    AccessToken getByUserIdAndClientId(@Param("user_id") Integer user_id, @Param("client_id") Integer client_id);

    @Select("select" + FIELD_ACCESS_TOKEN + "from sso_access_token where access_token = #{access_token}")
    AccessToken getByAccessToken(String access_token);

    @Select("select" + FIELD_ACCESS_TOKEN + "from sso_access_token where id = #{id}")
    AccessToken getAccessTokenByPK(Integer id);

    @Select("select" + FIELD_REFRESH_TOKEN + "from sso_refresh_token where token_id = #{token_id}")
    RefreshToken getByTokenId(Integer token_id);

    @Select("select" + FIELD_REFRESH_TOKEN + "from sso_refresh_token where refresh_token = #{refresh_token}")
    RefreshToken getByRefreshToken(String refresh_token);

    @Insert("insert into sso_access_token(" + FIELD_ACCESS_TOKEN + ") values(" + FIELD_ACCESS_TOKEN_VALUE + ")")
    int saveAccessToken(AccessToken accessToken);

    @Insert("insert into sso_refresh_token(" + FIELD_REFRESH_TOKEN + ") values(" + FIELD_REFRESH_TOKEN_VALUE + ")")
    int saveRefreshToken(RefreshToken refreshToken);

    @Update("update sso_access_token set access_token=#{accessToken},expires_in=#{expiresIn} where id=#{id}")
    int updateAccessTokenByPK(AccessToken accessToken);

    @Update("update sso_refresh_token set refresh_token=#{refreshToken},expires_in=#{expiresIn} where id=#{id}")
    int updateRefreshTokenByPK(RefreshToken refreshToken);
}