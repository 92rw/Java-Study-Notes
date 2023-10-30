package com.exercise.spring.aspectj;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


@Component
@Aspect
public class MathAspect {

    @Pointcut(value = "execution(public Number IntegerCalc.getSum(Number, Number))")
    public void myPointCut(){}

    @Before(value = "myPointCut()")
    public void showBeignLog(JoinPoint joinPoint) {
        System.out.println("[日志] 方法" + joinPoint.getSignature().getName() + "开始执行");
    }

    @After(value = "myPointCut()")
    public void showFinallyEndLog(JoinPoint joinPoint) {
        System.out.println("[日志] 方法" + joinPoint.getSignature().getName() + "执行完毕");
    }
}

