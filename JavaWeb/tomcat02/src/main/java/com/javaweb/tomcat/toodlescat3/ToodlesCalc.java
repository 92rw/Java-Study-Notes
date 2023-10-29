package com.javaweb.tomcat.toodlescat3;

import com.javaweb.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ToodlesCalc extends toodlesHttpServlet{
    @Override
    public void doGet(myHttpRequest request, myHttpResponse response) {
        //业务代码，完成计算任务
        int num1 = WebUtils.parseInt(request.getParameter("num1"),0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"),0);

        int sum = num1 + num2;

        //返回计算结果给浏览器
        OutputStream outputStream = response.getOutputStream();
        String resMsg = myHttpResponse.responseHeader + "<h1>Success</h1>ToodlesCat V3版连接成功<br><br>" +
                 String.format("<h2>计算结果为%d + %d = %d</h2>", num1, num2, sum);
        try {
            outputStream.write(resMsg.getBytes());//将字符串以byte[]形式返回
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(myHttpRequest request, myHttpResponse response) {
        this.doGet(request,response);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
