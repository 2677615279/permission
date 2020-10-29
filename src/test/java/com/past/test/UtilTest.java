package com.past.test;

import com.past.beans.Mail;
import com.past.util.IpUtil;
import com.past.util.MD5Util;
import com.past.util.MailUtil;
import com.past.util.PasswordUtil;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UtilTest {

    /**
     * 测试随机生成字母数字交错的密码  长度为int型，范围[8,10]
     * 此处每生成一次  使线程休眠100ms  以防2次生成同样的密码
     * @throws Exception
     */
    @Test
    public void testPassword() throws Exception{

        System.out.println(PasswordUtil.randomPassword()); //F4a8V4H2
        Thread.sleep(100);
        System.out.println(PasswordUtil.randomPassword()); //V9y5w2u7f7
        Thread.sleep(100);
        System.out.println(PasswordUtil.randomPassword()); //K2E3a9e4E

    }


    /**
     * 测试MD5加密
     * @throws Exception
     */
    @Test
    public void testMD5() throws Exception{

        System.out.println(MD5Util.encrypt("123456"));

    }


    /**
     * 测试ip工具类
     * @throws Exception
     */
    @Test
    public void testIP() throws Exception{

        String ip = IpUtil.getLocalIPForCMD();
        System.out.println(ip); //内网ipv4地址 192.168.0.113

        System.out.println(IpUtil.getV4IP()); //公网ipv4地址 116.52.177.138

    }


    /**
     * 测试邮件工具类
     * @throws Exception
     */
    @Test
    public void testMail() throws Exception{

        Set<String> set = new HashSet<>();
        set.add("1654849544@qq.com");
        Mail mail = Mail.builder().subject("TestMail").message("TESTTEST").receivers(set).build();

        MailUtil.send(mail);

    }


}
