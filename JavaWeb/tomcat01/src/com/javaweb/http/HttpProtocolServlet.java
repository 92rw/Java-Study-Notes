package com.javaweb.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 演示GET和POST方式访问服务器时的返回数据
 */
public class HttpProtocolServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //服务器端输出
        System.out.println("服务器端的 doPost()方法被调用");
        //要求：输出一句话返回给浏览器
        //实现过程：利用HttpServletResponse类的getWriter()方法，利用输出流回复数据
        //细节说明：为了让浏览器显示中文，需要给回送数据设置设置编码类型是utf-8。
        // MIME说明数据格式是text类型下的html格式，后面设置的是编码。
        // 需要设置在输出流之前，否则无法生效
        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.print("<h1>POST方式登陆成功</h1>");

        //为了确保数据正确返回，可以使用这两个方法
        writer.flush();//将缓存的数据进行刷新
        writer.close();//及时释放资源
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //服务器端输出
        System.out.println("服务器端的 doGet()方法被调用");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.print("<h1>GET方式登陆成功</h1>");

        writer.flush();
        writer.close();
    }
}
