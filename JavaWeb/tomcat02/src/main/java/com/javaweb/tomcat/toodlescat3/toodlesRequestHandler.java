package com.javaweb.tomcat.toodlescat3;

import com.javaweb.utils.WebUtils;

import java.io.*;
import java.net.Socket;

/**
 * 自己实现的HttpRequestHandler类，是用于处理http请求的线程对象（ToodlesCatV3版）
 */
public class toodlesRequestHandler implements Runnable{

    //定义Socket
    private Socket socket = null;

    public toodlesRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String resMsg = "";
            //和客户端进行交互：将客户端的字节流转换为字符流接收，以便按行读取
            InputStream inputStream = socket.getInputStream();

            //V3版中，将读取读取浏览器数据的任务交给myHttpRequest类完成，仅输出处理结果
            myHttpRequest myHttpRequest = new myHttpRequest(inputStream);

            System.out.println("线程 " + Thread.currentThread().getName() + " 接收到到浏览器发来的数据 " + myHttpRequest);

            //向客户端返回数据（这个功能在Servlet实现，此处仅做测试）

            //构建响应体
//            String responseBody = myHttpResponse.responseHeader + "<h1>Success</h1>ToodlesCat V3版连接成功<br>" +
//                    "<br>服务器接收到数据" + myHttpRequest;
//

            myHttpResponse myHttpResponse = new myHttpResponse(socket.getOutputStream());
            String uri = myHttpRequest.getUri();


            String servetName = ToodlesCatV3.uriPatternMapping.get(uri);
            //ConcurrentHashMap的get()方法不能传入null作为参数
            if (servetName == null) servetName = "";
            //根据动态绑定，真正的运行类型是实现这个抽象类的ToodlesCalc类对象
            toodlesHttpServlet httpServlet = ToodlesCatV3.servletMapping.get(servetName);

            if (httpServlet != null) {
                //根据方法的绑定机制，最终也会调用到doGet方法
                httpServlet.service(myHttpRequest, myHttpResponse);
            } else {
                if (WebUtils.isHtml(uri)) {
                    //因为uri前面带一个斜杠，在此需要处理掉再传给readHtml方法
                    resMsg = myHttpResponse.responseHeader + WebUtils.readHtml(uri.substring(1));
                } else {//没有找到Servlet时，返回404
                    resMsg = myHttpResponse.responseHeader + "<h1>404 Not Found</h1>";
                }
                OutputStream outputStream = myHttpResponse.getOutputStream();
                outputStream.write(resMsg.getBytes());//将字符串以byte[]形式返回
                outputStream.flush();
                outputStream.close();
            }
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //如果socket不关闭，会占用服务器资源，导致后续的用户端连接超时
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
