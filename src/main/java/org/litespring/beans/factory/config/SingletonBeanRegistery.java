package org.litespring.beans.factory.config;

/**
 * 注册单例对象
 */
public interface SingletonBeanRegistery {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

}
