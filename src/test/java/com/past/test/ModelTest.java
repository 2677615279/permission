package com.past.test;

import com.past.model.SysDept;
import com.past.model.SysUser;
import org.junit.Test;

import java.util.Date;

public class ModelTest {

    @Test
    public void test(){

        SysUser user = new SysUser();
        user.setId(0);
        user.setPassword("111");
        user.setOperateTime(new Date());
        System.out.println(user);

    }

    /**
     * 使用Builder方法初始化对象
     */
    @Test
    public void testByBuilder(){

        SysDept sysDept = SysDept.builder().id(0).name("OK").build();
        System.out.println(sysDept);

    }


}
