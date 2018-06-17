package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanCreatingException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.utils.ClassUtils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    private final String BEAN_ID = "id";
    private final String BEAN_CLASSNAME = "class";

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        InputStream inputStream;

        try{
            ClassLoader c1 = ClassUtils.getDefaultClassLoader();
            inputStream = c1.getResourceAsStream(configFile);

            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            //beans
            Element rootElement = document.getRootElement();
            Iterator elementIterator = rootElement.elementIterator();

            while (elementIterator.hasNext()){
                Element element = (Element) elementIterator.next();
                String id = element.attributeValue(BEAN_ID);
                String beanClassName = element.attributeValue(BEAN_CLASSNAME);
                BeanDefinition definition = new GenericBeanFactory(id, beanClassName);

                this.beanDefinitionMap.put(id, definition);
            }

        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("parse xml occurs exception" + configFile, e);
        }
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
}
