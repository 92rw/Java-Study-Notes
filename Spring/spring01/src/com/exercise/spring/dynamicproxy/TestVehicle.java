package com.exercise.spring.dynamicproxy;

import org.junit.jupiter.api.Test;

public class TestVehicle {
    @Test
    public void proxyRun() {
        //创建Vehicle实例对象
        Vehicle commute_8416 = new Commute_8416();

        //创建 VehicleProxyProvider 实例和要代理的 Vehicle 实例
        VehicleProxyProvider provider = new VehicleProxyProvider(commute_8416);

        //获取代理对象，该对象可以代理执行
        //proxy实例的编译类型是 Vehicle，运行类型是代理类型
        Vehicle proxy = provider.getProxy();
        proxy.run();
    }
}
