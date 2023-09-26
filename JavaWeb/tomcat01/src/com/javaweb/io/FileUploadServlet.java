package com.javaweb.io;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/uploadfile")
public class FileUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断前端页面发来的是不是文件表单
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建用于构建一个解析数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            //如果包含中文字符，将出现乱码，因此进行处理
            servletFileUpload.setHeaderEncoding("utf-8");

            //servletFileUpload对象可以把表单提交的文件封装到FileItem文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //System.out.println("list的内容是：" + list);//可查询到 list 中存储的是前端发来的图片和说明
                for (FileItem fileItem : list) {
                    //System.out.println("fileItem：" + fileItem);//可以看到存储的数据中包含哪些内容信息
                    if (fileItem.isFormField()){//如果为真，说明是文本
                        String description = fileItem.getString("utf-8");//如果不设置编码格式，会出现乱码
                        System.out.println("图片说明：" + description);
                    } else {
                        //获取上传的文件名
                        String fileName = fileItem.getName();
                        System.out.println("上传的文件名：" + fileName);
                        //1.把上传到服务器temp目录下的文件保存到指定位置
                        String filePath = "/upload/";
                        //2.获取完整目录，和项目运行环境绑定，是动态的
                        String fullPath = request.getServletContext().getRealPath(filePath);
                        String realPath = fullPath + WebUtils.getYearMonthDay();
                        System.out.println("保存到目标路径：" + realPath);
                        //3.如果目录不存在，就新建创建
                        File pathDirectory = new File(realPath);
                        if (!pathDirectory.exists()) {
                            pathDirectory.mkdirs();
                        }
                        //4.将文件拷贝到设定的保存地址
                        //File类型的对象会把地址末尾的斜杠拿掉，因此在输出目录时需要加一个斜杠
                        //如果文件重名，调用write方法会覆盖现有文件，因此给文件名加前缀，保证唯一性
                        fileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + fileName;
                        String fileFullPath = pathDirectory +"/" + fileName;
                        fileItem.write(new File(fileFullPath));
                    }
                }
                //5.向前端页面返回信息
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("文件上传成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
