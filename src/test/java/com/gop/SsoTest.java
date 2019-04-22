/**
 * @Author Ljm
 * @Date 2019/4/10
 */
package com.gop;

import com.gop.dao.SsoDao;
import com.gop.pojo.AccessToken;
import com.gop.pojo.RefreshToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SsoTest {
    @Autowired
    private SsoDao ssoDao;

    @Test
    public void getByRedirectUrI() {
        System.out.println(ssoDao.getByRedirectUrI("http://127.0.0.1:7000/user/userIndex"));
    }

    @Test
    public void getByUserIdAndClientId() {
        System.out.println(ssoDao.getByUserIdAndClientId(1, 2));
    }

    @Test
    public void getByAccessToken() {
        System.out.println(ssoDao.getByAccessToken("11.0e7baee3e290429b54a5692a4eee8af5f99a9862.2592000.1538210962"));
    }

    @Test
    public void getAccessTokenByPK() {
        System.out.println(ssoDao.getAccessTokenByPK(1));
    }

    @Test
    public void getByTokenId() {
        System.out.println(ssoDao.getByTokenId(1));
    }

    @Test
    public void getByRefreshToken() {
        System.out.println(ssoDao.getByRefreshToken("12.143a279cb81b0e5063af9912f346ae16e49c17e2.31536000.1567154963"));
    }

    @Test
    public void saveAccessToken() {
        AccessToken accessToken = new AccessToken();
        accessToken.setId(6);
        accessToken.setAccessToken("111");
        accessToken.setExpiresIn(345L);
        accessToken.setUserId(2);
        accessToken.setClientId(2);
        System.out.println(ssoDao.saveAccessToken(accessToken));
    }

    @Test
    public void saveRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(8);
        refreshToken.setRefreshToken("222");
        refreshToken.setExpiresIn(456L);
        refreshToken.setTokenId(6);
        refreshToken.setCreateUser(2);
        System.out.println(ssoDao.saveRefreshToken(refreshToken));
    }

    @Test
    public void updateAccessTokenByPK() {
        AccessToken accessToken = new AccessToken();
        accessToken.setId(6);
        accessToken.setAccessToken("1111");
        accessToken.setExpiresIn(3456L);
        System.out.println(ssoDao.updateAccessTokenByPK(accessToken));
    }

    @Test
    public void updateRefreshTokenByPK() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(8);
        refreshToken.setRefreshToken("2222");
        refreshToken.setExpiresIn(4567L);
        System.out.println(ssoDao.updateRefreshTokenByPK(refreshToken));
    }
}