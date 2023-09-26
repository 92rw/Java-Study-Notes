package com.javaweb.jsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns ="/queryServlet")
public class QueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备要显示的数据，后续从DB数据库获取
        ArrayList<Station> list = new ArrayList<>();
        list.add(new Station(18074,"沙岭庄","SLZ"));
        list.add(new Station(18062,"娄山","LSH"));
        list.add(new Station(18056,"城阳","CYA"));

        //把list放入request域，供jsp页面使用
        request.setAttribute("stations",list);

        //请求转发
        request.getRequestDispatcher("/render/jstl/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //让Get方法和Post方法得到相同的结果
        doPost(request,response);
    }
}