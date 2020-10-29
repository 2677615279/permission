package com.past.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取Spring上下文--ApplicationContext
 */
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    private static  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {

        applicationContext = context;

    }

    /**
     * 根据传入的类型 从applicationContext中取出spring容器的bean
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T popBean(Class<T> cla) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(cla);
    }

    /**
     * 根据传入的bean名称和类型 从applicationContext中取出spring容器的bean
     * @param name
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T popBean(String name, Class<T> cla) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, cla);
    }

}
