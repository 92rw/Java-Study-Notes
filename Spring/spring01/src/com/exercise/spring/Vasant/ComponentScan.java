package com.exercise.spring.Vasant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)           //注解的修饰对象
@Retention(RetentionPolicy.RUNTIME) //注解保留时间
public @interface ComponentScan {
    String value() default "";      //可以传入value属性
}
