package com.javaweb.tomcat.toodlescat2;

import java.io.*;
import java.net.Socket;

/**
 * 自己实现的HttpRequestHandler类，是用于处理http请求的线程对象（ToodlesCatV2版）
 */
public class myRequestHandler implements Runnable{

    //定义Socket
    private Socket socket = null;

    public myRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("建立和客户端的连接，当前线程编号" + Thread.currentThread().getName());

            //和客户端进行交互：将客户端的字节流转换为字符流接收，以便按行读取
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String mes = null;
            System.out.println("======接收到浏览器发送的数据======");
            while ((mes = bufferedReader.readLine()) != null) {
                if (mes.length() == 0) {
                    break;
                }
                System.out.println(mes);
            }

            //向客户端返回数据

            //构建响应内容：响应头+响应体
            String responseHeader = "HTTP/1.1 200 OK\r\n" +//响应行
                    "Content-Type: text/html;charset=utf-8\r\n\r\n";//响应头和响应体
            String responseBody = responseHeader + "<h1>Success</h1>ToodlesCat V2版连接成功";
            //字节输出流
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(responseBody.getBytes());//将字符串以byte[]形式返回

            System.out.println("======向浏览器回送的数据======");
            System.out.println(responseBody);

            outputStream.flush();
            outputStream.close();
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
