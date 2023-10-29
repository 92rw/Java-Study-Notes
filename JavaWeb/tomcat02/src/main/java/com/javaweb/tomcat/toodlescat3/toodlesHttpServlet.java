package com.javaweb.tomcat.toodlescat3;

import java.io.IOException;

public abstract class toodlesHttpServlet implements toodlesServlet {
    //采用模板设计模式，让service方法绑定到真正的实现方法
    @Override
    public void service(myHttpRequest request, myHttpResponse response) throws IOException {
        //equalsIgnoreCase() 比较字符串内容是否相同，且不区分大小写
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            this.doGet(request,response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            this.doPost(request,response);
        }
    }
    //设置为抽象方法，让继承的子类（ToodlesCalc）实现这些方法
    public abstract void doGet(myHttpRequest request, myHttpResponse response);
    public abstract void doPost(myHttpRequest request, myHttpResponse response);
}
