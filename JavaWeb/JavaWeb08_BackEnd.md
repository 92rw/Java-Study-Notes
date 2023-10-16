# WEB开发

## 含义

1. WEB，在英语中Web表示网/网络资源（页面，图片，css，js），它用于表示WEB服务器（主机）供浏览器访问的资源
2. WEB服务器（主机）上供外界访问的Web资源分为：
   * 静态web资源（如html页面）：指web页面中供人们浏览的数据，始终是不变。
   * 动态web资源，比如Servlet（Java）、PHP等。

3. 静态web资源开发技术
   * Html
   * css等
4. .常用动态web资源开发技术：
   * Servlet
   * SpringBoot
   * SpringMVC
   * PHP
   * ASP.NET等

## 技术栈

BS开发：Browser浏览器Server服务端

（1）兼容性，因为浏览器的种类很多，发现你写的程序，在某个浏览器会出现问题，其它浏览器正常

（2）安全性，通常情况下，BS安全性不如CS好控制

（3）易用性，BS好于CS，浏览器电脑有

（4）扩展性，BS相对统一，只需要写Server

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 自己写的web服务，可以将 Monkey-game.html文件返回给浏览器
 */
public class WebResponse {
    public static void main(String[] args) throws IOException {

        //1.在9999端口监听
        ServerSocket serverSocket = new ServerSocket(9999);

        //如果serverSocket 没有关闭，就等待连接, 不停的等待
        //不使用while循环的话，浏览器会提示连接失败，原因是浏览器尚未收到输出信息服务器端就关闭了连接
        while (!serverSocket.isClosed()) {
            System.out.println("=====我的web服务在 9999端口监听=====");
            //2. 等待浏览器/客户端连接, 得到socket用于通信
            Socket socket = serverSocket.accept();

            //3. 通过socket 得到 输出流
            OutputStream outputStream = socket.getOutputStream();
            //   返回给浏览器/客户端
            //4. 读取 hello.html 文件返回即可=> 如何读取文件内容
            //   得到文件输入流(字符输入流), 和 src/Monkey-game.html
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader("src/Monkey-game.html"));
            String buf = "";
            //   循环读取hello.html
            while ((buf = bufferedReader.readLine()) != null) {
                outputStream.write(buf.getBytes());//字节输出流需要将字符串转换为字节码
            }

            outputStream.close();
            socket.close();
        }
        serverSocket.close();
    }
}

```

CS开发：Client客户端Server服务端

服务器的逻辑、架构不变，变化体现在交互的数据和展示的页面。安装了JavaWeb服务软件主机称为Web服务器/JavaWeb服务器，在web服务器中开发相应的web资源。



## 常用的JavaWeb服务软件

1. Tomcat：由Apache组织提供的一种Web服务器，提供对jsp和Servlet的支持。它是一种轻量级的javaWeb容器，是当前应用最广的JavaWeb服务器（免费）
2. Jboss：是一个遵从JavaEE规范的、它支持所有的JavaEE规范（免费）
3. GlassFish：由Oracle公司开发的一款JavaWeb服务器，是一款商业服务器，达到产品级质量（应用很少）
4. Resin：是CAUCHO公司的产品，是一个非常流行的服务器，对servlet和JSP提供了良好的支持，性能也比较优良（收费）
5. WebLogic：是Oracle公司的产品，支持JavaEE规范，而且不断的完善以适应新的开发要求，适合大型项目（收费，用的不多，适合大公司）



### WEB应用

·什么是Web应用

1.WEB应用是多个web资源的集合。简单的说，可以把web应用理解为硬盘上的一个目录，这个目录用于管理多个web资源。

2.Web应用通常也称之为web应用程序，或web工程，通俗的说就是网站。

·WEb应用组成

一个WEB应用由多个WEB资源或其它文件组成，包括html文件、css文件、js文件、动态web页面、java程序、支持jar包、配置文件等。开发人员在开发web应用时，按照规定目录结构存放这些文件。否则，在把web应用交给web服务器管理时，不仅可能会使web应用无法访问，还会导致web服务器启动报错