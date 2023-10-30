package com.exercise.spring.test;

import com.exercise.spring.bean.BookStore;
import com.exercise.spring.bean.Emp;
import com.exercise.spring.bean.Master;
import com.exercise.spring.bean.Monster;
import com.exercise.spring.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class SpringBeanTest {
    @Test
    public void getBeanByCollection() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Emp iocBean = ioc.getBean("emp", Emp.class);

        System.out.println(iocBean);
    }


    @Test
    public void getBeanByRef() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        MemberServiceImpl iocBean = ioc.getBean("memberService2", MemberServiceImpl.class);

        iocBean.add();
    }

    @Test
    public void showLifeCircle() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans02.xml");
        Monster iocBean = ioc.getBean("monster08",Monster.class);

        System.out.println("测试方法获得的实例对象：" + iocBean);
        ((ConfigurableApplicationContext)ioc).close();
    }

    @Test
    public void getBeanByConstructor() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
        Monster iocBean = ioc.getBean("monster02",Monster.class);

        System.out.println(iocBean);
    }

    @Test
    public void getBeanByProperties() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans02.xml");
        Monster iocBean = ioc.getBean("monster09",Monster.class);

        System.out.println(iocBean);
    }

    @Test
    public void getBeanById() {
        //创建容器 ApplicationContext，该容器和配置文件关联
        ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");

        //方法一：通过 getBean(String s) 方法获取编译类型是 Object 类的对象
        Object monster1 = ioc.getBean("monster01");
        System.out.println(monster1.getClass());//运行类型是 Monster

        //方法二：通过 getBean(String s, Class<T> aClass) 方法获取实际类型
        Monster monster2 = ioc.getBean("monster01", Monster.class);
        System.out.println(monster1 == monster2);//两个方法得到的对象相同
    }

    @Test
    public void getPath() {
        File file = new File(this.getClass().getResource("/").getPath());
        System.out.println(file);
    }
}
