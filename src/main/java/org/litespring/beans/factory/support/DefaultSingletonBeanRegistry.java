package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistery;
import org.litespring.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistery {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    public void registerSingleton(String beanName, Object singletonObject) {

        Assert.notNull(beanName, "'beanName' must not be null");

        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        this.singletonObjects.put(beanName, singletonObject);

    }

    public Object getSingleton(String beanName) {

        return this.singletonObjects.get(beanName);
    }


}