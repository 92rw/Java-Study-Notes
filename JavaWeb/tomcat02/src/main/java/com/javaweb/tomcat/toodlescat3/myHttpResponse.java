package com.javaweb.tomcat.toodlescat3;

import java.io.OutputStream;

/**
 * 封装和socket关联的输出流，向客户端返回http响应
 * 作用等价于原生的HttpServletResponse
 */
public class myHttpResponse {
    private OutputStream outputStream;
    //构建响应头
    public static final String responseHeader = "HTTP/1.1 200 OK\r\n" +//响应行
            "Content-Type: text/html;charset=utf-8\r\n\r\n";//响应头

    //构造器中传入的对象需要和socket关联
    public myHttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    //需要给浏览器返回数据时，可以调用这个方法
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
