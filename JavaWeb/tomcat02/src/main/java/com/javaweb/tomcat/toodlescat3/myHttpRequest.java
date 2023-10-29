package com.javaweb.tomcat.toodlescat3;

import java.io.*;
import java.util.HashMap;

/**
 * 在浏览器用get方法向服务器发送请求时，数据中包含method,uri,参数列表
 * 因此构建myHttpRequest类模拟原生的HttpServletRequest
 * 当前只考虑从请求行中获取GET请求信息
 */
public class myHttpRequest {
    //存放浏览器发来的数据
    private String method;
    private String uri;
    //对于参数列表，使用HashMap进行存放
    private HashMap<String,String> parameters = new HashMap<>();
    private InputStream input = null;

    public InputStream getInputStream() {
        return input;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    /**
     * 得到参数值
     * @param name 根据传入的参数名，在HashMap中查询参数值并返回
     * @return 当Servlet从请求头中找不到参数时，返回空字符串的根本原因
     */
    public String getParameter(String name) {
        if (parameters.containsKey(name)) {
            return parameters.get(name);
        } else {
            return null;
        }
    }
    //构造器中传入socket
    public myHttpRequest(InputStream input) {
        this.input = input;
        init();
    }

    /**
     * 将http请求的相关数据，进行封装,然后提供相关的方法，进行获取
     */
    private void init() {
        //注意：传入字节流时需要指定编码类型，否则出现乱码
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input,"utf-8"));
            //请求行的内容是"GET /calc?num1=1&num2=1 HTTP/1.1"，因此可用空格分开，以便获取其中的子串
            String[] requestLine = bufferedReader.readLine().split(" ");


            //得到请求的方法
            method = requestLine[0];
            //得到浏览器action中的地址
            int index = requestLine[1].indexOf("?");
            if (index == -1) {//没有参数列表
                uri = requestLine[1];
            } else {//存在参数列表
                uri = requestLine[1].substring(0, index);
                String params = requestLine[1].substring(index + 1);
                //获取参数中的键值对
                String[] paramPairs = params.split("&");
                //防止用户提交的地址没有参数
                if (null != paramPairs && !"".equals(parameters)) {
                    for (String paramPair : paramPairs) {
                        String[] paramKeyValue = paramPair.split("=");
                        if (paramKeyValue.length == 2) {parameters.put(paramKeyValue[0],paramKeyValue[1]);}
                    }
                }
            }
            //说明：此处不能关闭输入流，因为流和socket关联
            //input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "myHttpRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
