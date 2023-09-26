package com.javaweb.jsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns ="/calServlet")
public class CalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //客户端可以绕过浏览器的校验机制，因此需要对请求数据进行判断，如果数据有误则返回给calc.jsp

        //接收输入的数据
        int num1 = WebUtils.parseInt(request.getParameter("num1"),0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"),0);
        String oper = request.getParameter("oper");

        //完成计算
        double res = 0;//使用变量接收运算结果
        switch (oper) {
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num1 - num2;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = (double)num1 / num2;//需要强制转型
                break;
            default:
                System.out.println("运算符不正确");
        }

        //将结果保存到域对象：因为一次请求对应一次计算，因此将结果保存到request中
        //如果直接用结果传参，那么将无法显示计算式，因此把结果组织到一个字符串中
        String formula = String.format("%d %s %d = %s", num1, oper, num2, res);
        request.setAttribute("res", formula);
        //转发到显示页面calRes.jsp
        request.getRequestDispatcher("/render/calres.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}