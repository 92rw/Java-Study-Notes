package com.exercise.spring.aspectj;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ConcurrentHashMap;

public class TestAspect {
    @Test
    public void intcalcTestByProxy() {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("beans03.xml");
        Calculate integerCalc = (Calculate) ioc.getBean("integerCalc");
        integerCalc.getSum(3,3);
    }
}
