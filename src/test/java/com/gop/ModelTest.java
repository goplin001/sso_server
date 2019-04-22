/**
 * @Author Ljm
 * @Date 2019/4/10
 */
package com.gop;

import com.gop.dao.ModelDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ModelTest {
    @Autowired
    private ModelDao modelDao;

    @Test
    public void getModelByAccessToken() {
        System.out.println(modelDao.getModelByAccessToken("11.0e7baee3e290429b54a5692a4eee8af5f99a9862.2592000.1538210962"));
    }

    @Test
    public void getModelByRefresgToken() {
        System.out.println(modelDao.getModelByRefresgToken("12.143a279cb81b0e5063af9912f346ae16e49c17e2.31536000.1567154963"));
    }
}