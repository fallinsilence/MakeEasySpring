package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreatingException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    private DefaultBeanFactory factory = null;
    private XmlBeanDefinitionReader reader = null;

    /**
     * 所有测试用例初始化参数
     */
    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    /**
     * 给定一个xml配置文件，要能获取bean的定义
     */
    @Test
    public void getBeanDefinition(){
        reader.loadBeanDefinitions(new ClassPathResource("petstore_v1.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        // 单例为真
        Assert.assertTrue(beanDefinition.isSingleton());
        // 原型为假
        Assert.assertFalse(beanDefinition.isPrototype());
        // scope的值为""
        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT, beanDefinition.getScope());

        assertEquals("org.litespring.service.v1.PetStoreService", beanDefinition.getBeanClassName());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");
        Assert.assertEquals(petStore, petStore1);
    }

    /**
     * 获取一个不存在的bean对象
     */
    @Test
    public void testInvalidBean(){
        try{
            reader.loadBeanDefinitions(new ClassPathResource("petstore_v1.xml"));
            factory.getBean("invalidBean");
        } catch (BeanCreatingException e){
            return;
        }
        //如果走到该分支，说明没有捕获到异常，测试失败
        Assert.fail("except BeanCreatingException");
    }

    /**
     * 解析不存在的xml文件
     */
    @Test
    public void testInvalidXML(){
        try{
            reader.loadBeanDefinitions(new ClassPathResource("petstore_v11.xml"));
        }catch (BeanDefinitionStoreException e){
            return;
        }

        Assert.fail("expect BeanDefinitionStoreException");
    }

    /**
     * scope为原型，是否创建了两个对象
     */
    @Test
    public void getPrototypeBeanTest(){

        reader.loadBeanDefinitions(new ClassPathResource("petstore_v1.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore2");

        Assert.assertTrue(beanDefinition.isPrototype());
        Assert.assertFalse(beanDefinition.isSingleton());
        Assert.assertEquals(BeanDefinition.SCOPE_PROTOTYPE, beanDefinition.getScope());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore2");

        PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore2");
        Assert.assertNotEquals(petStore, petStore1);
    }
}
