package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreatingException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    /**
     * 给定一个xml配置文件，要能获取bean的定义
     */
    @Test
    public void getBeanDefinition(){

        BeanFactory factory = new DefaultBeanFactory("petstore_v1.xml");
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        assertEquals("org.litespring.service.v1.PetStoreService", beanDefinition.getBeanClassName());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);

    }

    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore_v1.xml");
        try{
            factory.getBean("invalidBean");
        } catch (BeanCreatingException e){
            return;
        }
        //如果走到该分支，说明没有捕获到异常，测试失败
        Assert.fail("except BeanCreatingException");
    }

    @Test
    public void testInvalidXML(){
        try{
            BeanFactory factory = new DefaultBeanFactory("xxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }

        Assert.fail("except BeanDefinitionStoreException");
    }
}
