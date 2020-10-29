package com.past.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 邮件规范条件类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {

    private String subject;//邮件主题

    private String message;//邮件信息

    private Set<String> receivers;//收件人邮箱

    @Override
    public String toString() {
        return "Mail{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", receivers=" + receivers +
                '}';
    }


}
