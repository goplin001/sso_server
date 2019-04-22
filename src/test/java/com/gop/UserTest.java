/**
 * @Author Ljm
 * @Date 2019/4/10
 */
package com.gop;

import com.gop.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void getByUsername(){
        System.out.println(userDao.getByUsername("Tom"));
    }

    @Test
    public void getByPK(){
        System.out.println(userDao.getByPK(2));
    }
}