package com.exercise.spring.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class VehicleProxyProvider {
    private Vehicle targetVehicle;

    public VehicleProxyProvider(Vehicle targetVehicle) {
        this.targetVehicle = targetVehicle;
    }

    public Vehicle getProxy() {
        //得到类加载器
        ClassLoader classLoader = targetVehicle.getClass().getClassLoader();

        //得到要代理的对象（被执行对象）的接口信息，底层通过接口完成调用
        Class<?>[] interfaces = targetVehicle.getClass().getInterfaces();

        //对于 InvocationHandler 接口，可以通过匿名对象的方式创建该对象
        //重写的 invoke 方法在处理 targetVehicle 对象时会调用
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("青岛站发出上行列车");
                //反射机制：通过方法反向调用对象
                Object result = method.invoke(targetVehicle, args);
                System.out.println("上行列车通过四方站");
                return result;
            }
        };

//        InvocationHandler invocationHandler = new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                Object invoke = method.invoke(proxy, args);
//                return invoke;
//            }
//        };

        Vehicle proxy = (Vehicle)Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        return proxy;
    }
}
