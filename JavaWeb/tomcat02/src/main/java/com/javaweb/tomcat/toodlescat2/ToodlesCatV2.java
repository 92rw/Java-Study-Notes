package com.javaweb.tomcat.toodlescat2;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ToodlesCatV2 {
    public static void main(String[] args) throws IOException {
        //建立监听端口
        ServerSocket serverSocket = new ServerSocket(12306);
        System.out.println("====== ToodlesCatV2 在12306端口监听======");
        while (!serverSocket.isClosed()) {
            //接收到浏览器/客户端连接后，得到数据通道socket
            Socket socket = serverSocket.accept();
            //创建线程对象，传入RequestHandler对象
            new Thread(new myRequestHandler(socket)).start();
        }
    }
}
