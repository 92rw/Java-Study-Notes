package com.exercise.spring.aop.xml;

import org.springframework.stereotype.Component;

public class DoubleCalc implements Calculate {
    @Override
    public Number getSum(Number n1, Number n2) {
        double result = (double) n1 + (double)n2;
        System.out.println("调用DoubleCalc类得到结果" + result);
        return result;
    }
}
