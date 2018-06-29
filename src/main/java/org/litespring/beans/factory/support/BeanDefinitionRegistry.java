package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    /**
     * 注册一个BeanDefinition（将该beanDefinition存入Map）
     */
    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);


    /**
     * 根据bean的ID得到bean的定义
     * @param beanID bean的Id
     * @return BeanDefinition类
     */
    BeanDefinition getBeanDefinition(String beanID);

}
