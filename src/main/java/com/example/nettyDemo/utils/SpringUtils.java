package com.example.nettyDemo.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author zl
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor {
    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = (T) beanFactory.getBean(clz);
        return result;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }


    public static <T> Map<String, T> getBeans(Class<T> clz){
        Map<String, T> beansOfType = beanFactory.getBeansOfType(clz);
        return beansOfType;
    }

    public static void allBean(){
        Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
        while (beanNamesIterator.hasNext()){
            System.out.println(beanNamesIterator.next());
        }
    }
}
