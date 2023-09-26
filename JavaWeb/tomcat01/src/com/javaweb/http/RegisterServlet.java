package com.javaweb.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RegisterServlet 被调用...");
        /**
         * <form action="http://localhost:8080/servlet/registerServlet" method="post">
         *     用户注册信息<br/>
         *     用户名称: <input type="text" name="username"><br/>
         *     用户密码: <input type="password" name="pwd1"><br/>
         *     确认密码: <input type="password" name="pwd2"><br/>
         *     选择你喜欢的运动项目:
         *     <input type="checkbox" name="sport" value="lq">篮球<br/>
         *     <input type="checkbox" name="sport" value="zq" checked>足球<br/>
         *     <input type="checkbox" name="sport" value="sq" checked>手球<br/>
         *     请选择性别 :
         *     <input type="radio" name="gender" value="male">男<br/>
         *     <input type="radio" name="gender" value="female">女<br/>
         *     请选择城市:
         *     <select name="city">
         *         <option>--选择--</option>
         *         <option value="cd">成都</option>
         *         <option value="bj">北京</option>
         *         <option value="sh">上海</option>
         *     </select><br/>
         *     自我介绍:
         *     <textarea rows="6" cols="20"></textarea><br/>
         *     选择你的文件(头像)<input type="file" name="myfile"><br/>
         *     <input type="submit" value="提交"/> <input type="reset" value="重置"/>
         * </form>
         */
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        //在后端检查两次密码是否一致
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");

        //获取checkbox
        String[] sports = request.getParameterValues("sport");
        //对sports 处理
        String sportsStr = "";
        for (String sport : sports) {
            sportsStr += sport + " ";
        }
        //获取radio
        String gender = request.getParameter("gender");
        //获取select
        String city = request.getParameter("city");
        //获取textarea
        String info = request.getParameter("info");

        //返回给浏览器，回显
        response.setContentType("text/html;charset=utf-8");

        //解决乱码的方式
        //老韩解读
        //1. 设置服务器使用utf-8
        //response.setCharacterEncoding("utf-8");
        //2. 设置浏览器端是utf-8, 而且类型是 text/html
        //response.setHeader("Content-Type", "text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("username= " + username + "<br/>");
        writer.print("pwd1= " + pwd1 + "<br/>");
        writer.print("pwd2= " + pwd2 + "<br/>");
        writer.print("喜欢的运动= " + sportsStr + "<br/>");
        writer.print("gender= " + gender + "<br/>");
        writer.print("city= " + city + "<br/>");
        writer.print("info= " + info + "<br/>");
        writer.flush();
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}
