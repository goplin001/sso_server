/**
 * @Author Ljm
 * @Date 2019/4/9
 */
package com.gop.dao;

import com.gop.pojo.model.SsoModel;
import org.apache.ibatis.annotations.Select;

public interface ModelDao {
    @Select("select a.user_id,b.refresh_token from sso_access_token a left join sso_refresh_token b on a.id=b.token_id where a.access_token=#{access_token}")
    SsoModel getModelByAccessToken(String access_token);

    @Select("select a.expires_in,b.user_id,b.client_id from sso_refresh_token a left join sso_access_token b on a.token_id=b.id where a.refresh_token=#{refresh_token}")
    SsoModel getModelByRefresgToken(String refresh_token);
}