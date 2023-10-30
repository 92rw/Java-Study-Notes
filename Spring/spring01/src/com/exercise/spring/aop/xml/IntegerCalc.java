package com.exercise.spring.aop.xml;

import org.springframework.stereotype.Component;

public class IntegerCalc implements Calculate {
    @Override
    public Number getSum(Number n1, Number n2) {
        int result = (int) n1 + (int)n2;
        System.out.println("调用IntegerCalc类得到结果" + result);
        return result;
    }
}
