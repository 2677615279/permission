package com.past.util;

import com.past.beans.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 邮件工具类
 */
@Slf4j
public class MailUtil {

    public static boolean send(Mail mail) {

        // TODO
        //实际发送邮件的邮箱
        String from = "2677615279@qq.com";
        //发送邮件的邮箱端口，默认25
        int port = 25;
        //SMTP 服务器地址
        String host = "smtp.qq.com";
        //发送邮件的密码
        String pass = "hjxunlnbvjthdhhe"; //此处使用授权码
        //收件人收到邮件时看到的发件名称
        String nickname = "TestMail";

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setSmtpPort(port);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setMsg(mail.getMessage());
            email.send();
            log.info("{} 发送邮件到 {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "发送邮件到" + StringUtils.join(mail.getReceivers(), ",") + "失败", e);
            return false;
        }
    }

}

