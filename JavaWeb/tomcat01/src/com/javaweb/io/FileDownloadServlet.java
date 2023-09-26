package com.javaweb.io;

import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@WebServlet("/fileDownLoadServlet")
public class FileDownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");//保证前端发来的不是乱码
        String fileName = request.getParameter("name");

        //给http响应设置响应头Content-Type（MIME类型）
        ServletContext servletContext = request.getServletContext();
        String filePath = "/test/img/";
        String downloadPath = filePath + fileName;
        String mimeType = servletContext.getMimeType(downloadPath);//由程序得到具体的MIME类型
        System.out.println("文件的MIME类型：" + mimeType);
        response.setContentType(mimeType);

        //给http响应设置响应头Content-Disposition展示形式，采用attachment则使用下载方式
        //不同浏览器的识别方式不同，火狐需要base64，ie/Chrome是URL编码
        if (request.getHeader("User-Agent").contains("Firefox")) {
            // 火狐 Base64编码
            response.setHeader("Content-Disposition", "attachment; filename==?UTF-8?B?" +
                    new BASE64Encoder().encode(fileName.getBytes("UTF-8")) + "?=");
        } else {
            // 其他(主流ie/chrome)使用URL编码操作
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode(fileName, "UTF-8"));
        }

        //读取下载的文件数据，返回给前端
        //创建一个和下载文件关联的输入流
        InputStream resourceAsStream = servletContext.getResourceAsStream(downloadPath);
        //得到返回数据的输出流（按二进制处理）
        ServletOutputStream outputStream = response.getOutputStream();
        //使用工具类，将输入流关联的文件，对拷到输出流，返回给前端
        //此处需要引入org.apache.commons.io包的IOUtils工具类
        IOUtils.copy(resourceAsStream,outputStream);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
