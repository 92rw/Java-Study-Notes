package com.javaweb.tomcat.toodlescat1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 第一个版本的myTomcat，实现功能：接收浏览器请求并返回信息
 */
public class ToodlesCatV1 {
    public static void main(String[] args) throws IOException {
        //建立监听端口
        ServerSocket serverSocket = new ServerSocket(12306);
        System.out.println("====== ToodlesCatV1 在12306端口监听======");

        while (!serverSocket.isClosed()) {
            //等待浏览器/客户端连接
            Socket socket = serverSocket.accept();
            //接收客户端的数据，并转换成字符缓冲流
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String mes = null;
            System.out.println("======接收到浏览器发送的数据======");
            while ((mes = bufferedReader.readLine()) != null) {
                if (mes.length() == 0) {
                    break;
                }
                System.out.println(mes);
            }


            //按照Http响应的方式回送数据
            OutputStream outputStream = socket.getOutputStream();
            //构建响应体，\r\n表示回车换行
            String responseHeader = "HTTP/1.1 200 OK\r\n" +//响应行
              "Content-Type: text/html;charset=utf-8\r\n\n";//响应头和响应体间有一个空行
            String responseBody = responseHeader + "<h1>Success</h1>ToodlesCat V1版连接成功";
            outputStream.write(responseBody.getBytes());//将字符串以byte[]形式返回

            System.out.println("======向浏览器回送的数据======");
            System.out.println(responseBody);

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        }
    }
}
