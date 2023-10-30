package com.exercise.spring.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


public class MathAspect {

    public void showBeignLog(JoinPoint joinPoint) {
        System.out.println("[日志] 方法" + joinPoint.getSignature().getName() + "开始执行");
    }

    public void showFinallyEndLog(JoinPoint joinPoint) {
        System.out.println("[日志] 方法" + joinPoint.getSignature().getName() + "执行完毕");
    }

    public void showExceptionLog(JoinPoint joinPoint, Throwable throwable) {
        Signature signature = joinPoint.getSignature();//通过连接点对象 joinpoint，获取方法签名
        System.out.println("[日志] 执行方法" + signature.getName() + "出现异常：" + throwable);
    }

    public void showSuccessEndLog(JoinPoint joinPoint, Number res) {
        Signature signature = joinPoint.getSignature();//通过连接点对象 joinpoint，获取方法签名
        System.out.println("[日志] 执行方法" + signature.getName() + "得到的结果为" + res);
    }
}

