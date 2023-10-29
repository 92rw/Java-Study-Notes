package com.javaweb.tomcat.toodlescat3;

import java.io.IOException;

/**
 * 自己构建的Servlet接口，不配置ServletConfig属性、不实现getServletInfo()方法
 */
public interface toodlesServlet {
    void init() throws Exception;

    void service(myHttpRequest request, myHttpResponse response) throws IOException;

    void destroy();
}
