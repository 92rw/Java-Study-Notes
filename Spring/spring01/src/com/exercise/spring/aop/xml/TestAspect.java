package com.exercise.spring.aop.xml;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspect {
    @Test
    public void intcalcTestByProxy() {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("beans04.xml");
        Calculate calculate = ioc.getBean(Calculate.class);
        calculate.getSum(3.5,3.6);
    }


}
