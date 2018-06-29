package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreatingException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.utils.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 缺省的BeanFactory类
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private final String BEAN_ID = "id";
    private final String BEAN_CLASSNAME = "class";

    private ClassLoader beanClassLoader;
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = getBeanDefinition(beanId);

        if (beanDefinition == null)
            throw new BeanCreatingException("Bean Definition does not exist");

        if (beanDefinition.isSingleton()){
            Object bean = this.getSingleton(beanId);
            if (bean == null){
                bean = createBean(beanDefinition);
                this.registerSingleton(beanId, bean);
            }
            return bean;

        }

        return createBean(beanDefinition);

    }

    private Object createBean(BeanDefinition beanDefinition){
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();

        try{
            Class<?> loadClass = classLoader.loadClass(beanClassName);
            //调用这个方法，基于一个原则：我们假定这个类有一个缺省的无参构造方法
            return loadClass.newInstance();

        } catch (Exception e){
            throw new BeanCreatingException("creating bean " + beanClassName + "occurs exception");
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

}
