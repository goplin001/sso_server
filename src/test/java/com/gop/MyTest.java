/**
 * @Author Ljm
 * @Date 2019/4/8
 */
package com.gop;

import com.gop.enums.ExpireEnum;
import org.junit.Test;

public class MyTest {

    @Test
    public void aa() {
        System.out.println(ExpireEnum.ACCESS_TOKEN.getTime());
    }
}