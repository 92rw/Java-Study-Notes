package com.javaweb.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/signup"})
public class HttpRequestExample extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这里我们使用request对象，获取表单提交的各种数据
        System.out.println("HttpServletRequestMethods doPost() 被调用..");

        /***********************************
         *  获取和http请求头相关信息
         ***********************************/

        System.out.println("请求的资源路径URI= " + request.getRequestURI());
        //http://主机/uri
        System.out.println("请求的统一资源定位符（绝对路径）URL= " + request.getRequestURL());
        System.out.println("请求的客户端ip 地址= " + request.getRemoteAddr());//本地就是127.0.0.1


        // 说明，如果我们希望得到请求的头的相关信息，可以使用request.getHeader("请求头字段")
        System.out.println("http请求头HOST= " + request.getHeader("Host"));
        System.out.println("该请求的发起地址是= " + request.getHeader("Referer"));
        // 请获取访问网站的浏览器是什么？
        String userAgent = request.getHeader("User-Agent");
        System.out.println("User-Agent= " + userAgent);
        // 取出FireFox, 取出最后
        String[] s = userAgent.split(" ");
        System.out.println("浏览器=" + s[s.length - 1].split("\\/")[0]);
        //使用正则表达式得到操作系统的信息
        String regStr= "\\((.*?)\\)";//设置采用非贪婪匹配
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(userAgent);
        matcher.find();//只获取第一组，因此不用while循环
        String[] osInfo = matcher.group(1).split(";");

        System.out.println("操作系统类型" + osInfo[1].trim());//trim()方法去掉字符串中的空格

        //获取 Cookie（Edge的JSESSIONID和火狐不一样，会包含其他参数）
        String cookie = request.getHeader("Cookie");
        String JSESSIONID = cookie.split("=")[1];
        System.out.println("取出JSESSIONID= " + JSESSIONID);

        // 主要是Get / Post
        System.out.println("http请求方式~= " + request.getMethod());
        /***********************************
         *  获取和请求参数相关信息, 注意要求在返回数据前，获取参数
         ***********************************/

        //解决接收参数的中文乱码问题, 老师提示，写在 getParameter前.
        request.setCharacterEncoding("utf-8");
        //1. 获取表单的数据[单个数据]
        //username=tom&pwd=&hobby=hsp&hobby=spls
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");


        //2. 获取表单一组数据
        String[] agreements = request.getParameterValues("agreement");
        System.out.println("username= " + username);
        System.out.println("pwd= " + pwd);
        //增强for循环的快捷键 iter->回车即可 , 能使用快捷键，就使用快捷键
        for (String agreement : agreements) {
            System.out.println("agreement=" + agreement);
        }

        //返回接收到的信息， 给浏览器回显
        //本质就是在http响应头，加上 Content-Type: text/html;charset=utf-8
        //text/html 表示返回的数据类型，浏览器会根据这个类型来解析数据
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.printf("<h1>提交的用户名= %s</h1>",username);
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}

