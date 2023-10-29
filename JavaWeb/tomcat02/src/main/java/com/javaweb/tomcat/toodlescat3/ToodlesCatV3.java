package com.javaweb.tomcat.toodlescat3;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ToodlesCatV3 {
    //底层维护的容器，用于创建Servlet
    public static final ConcurrentHashMap<String, toodlesHttpServlet> servletMapping = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, String> uriPatternMapping = new ConcurrentHashMap<>();
    //用于实现session功能的容器
    public static final ConcurrentHashMap<String, String> sessionMapping = new ConcurrentHashMap<>();

    public void init() {
        //利用dom4j读取web.xml文件，首先需要得到web.xml文件的路径
        String path = ToodlesCatV3.class.getResource("/").getPath();
//        System.out.println("path= " +path);
        //使用dom4j技术读取xml文件
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(path + "web.xml"));
            System.out.println(document);
            //得到根元素
            Element rootElement = document.getRootElement();
            //得到根元素下面的所有元素
            List<Element> elements = rootElement.elements();
            //遍历并过滤，得到结果
            for (Element element : elements) {
                if ("servlet".equalsIgnoreCase(element.getName())) {
                    //使用反射将该实例放入到servletMapping
                    //得到node中的文本内容，以此创建实例
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    servletMapping.put(servletName.getText(),
                        (toodlesHttpServlet) Class.forName(servletClass.getText().trim()).newInstance());
                } else if  ("servlet-mapping".equalsIgnoreCase(element.getName())) {
                    Element uriPattern = element.element("url-pattern");
                    Element servletName = element.element("servlet-name");
                    uriPatternMapping.put(uriPattern.getText(), servletName.getText());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("servletMapping= " + servletMapping);
        System.out.println("uriPatternMapping= " + uriPatternMapping);

    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12306);
            System.out.println("====== ToodlesCatV3 在12306端口监听======");
            while (!serverSocket.isClosed()) {
                //接收到浏览器/客户端连接后，得到数据通道socket
                Socket socket = serverSocket.accept();
                //创建线程对象，传入RequestHandler对象
                new Thread(new toodlesRequestHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToodlesCatV3 toodlesCatV3 = new ToodlesCatV3();
        //读取xml文件
        toodlesCatV3.init();
        //启动运行容器
        toodlesCatV3.run();
    }
}
