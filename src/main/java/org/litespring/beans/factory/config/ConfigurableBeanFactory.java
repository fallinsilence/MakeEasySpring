package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * 一个可配置的factory，可以设置ClassLoader
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);
    ClassLoader getBeanClassLoader();

}