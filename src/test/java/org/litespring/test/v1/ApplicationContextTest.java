package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {

    /**
     * 测试能否从classpath读取到xml文件，获取bean对象
     */
    @Test
    public void getBeanByClasspathTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore_v1.xml");
        PetStoreService petStore = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStore);

    }

    /**
     * 测试能否从文件系统读取到xml文件，获取bean对象
     */
    @Test
    public void getBeanByFileSystemTest(){
        ApplicationContext context = new FileSystemApplicationContext("src/test/resources/petstore_v1.xml");

        PetStoreService petStore = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStore);

    }

}
