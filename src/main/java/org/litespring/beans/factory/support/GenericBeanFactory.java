package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

public class GenericBeanFactory implements BeanDefinition {

    private String id;
    private String beanClassName;

    public GenericBeanFactory(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
