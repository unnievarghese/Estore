package com.example.Estore.Estore;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Class is responsible for instantiating, configuring, and assembling the beans
 */
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    /**
     * Set the ApplicationContext that this object runs in.
     * @param context ApplicationContext
     * @throws BeansException Throws bean exceptions.
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CONTEXT = context;
    }

    /**
     * Return an instance of the specified bean.
     * @param beanName Name of the bean to be called.
     * @return Object
     */
    public static Object getBean(String beanName){
        return CONTEXT.getBean(beanName);
    }
}

