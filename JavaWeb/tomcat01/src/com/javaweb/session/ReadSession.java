package com.javaweb.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/ReadSession")
public class ReadSession extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ReadSession被调用");

        //如果没有Session，就会创建一个新的
        HttpSession session = request.getSession();

        System.out.println(session.getId());

        //读取Session
        Object version = session.getAttribute("Version");
        //读取的结果可能为空，需要判断
        if (version != null) {
            System.out.println("Version属性：" + version);
        } else {
            System.out.println("未找到属性，可能原Session已销毁");
        }
        //让Session会话立即超时
        session.invalidate();

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<h1>读取Session完成</h1>");
        writer.flush();//将缓存的数据进行刷新
        writer.close();//及时释放资源
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}