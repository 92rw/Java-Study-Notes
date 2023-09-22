package exercise.mainbatis.sqlsession;

import exercise.mainbatis.config.Function;
import exercise.mainbatis.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Configuration {
    //将类加载器作为私有属性
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    //读取xml文件信息并处理
    public Connection build (String resource) {
        Connection connection = null;
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            connection = resolveDataSource(root);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //解析xml文件的信息，并返回数据库连接
    private Connection resolveDataSource(Element node) {

        if ("database".equals(node)) {
            throw new RuntimeException("root 节点应当是 <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;

        //遍历node下的子节点
        for (Object property : node.elements("property")) {
            Element item = (Element) property;
            String name = item.attributeValue("name");
            String value = item.attributeValue("value");
            if (name == null || value == null) {
                throw new RuntimeException("property 节点应当设置 name 和 value 属性");
            }
            switch (name) {
                case "driverClassName":
                    driverClassName = value;
                    break;
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                default:
                    throw new RuntimeException("没有匹配到属性名");
            }
        }
        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public MapperBean readMapper(String filePath) {
        //形参filePath是从类的加载路径开始计算的相对路径
        MapperBean mapperBean = new MapperBean();
        InputStream inputStream = loader.getResourceAsStream(filePath);
        Element root = null;
        try {
            Document document = new SAXReader().read(inputStream);
            root = document.getRootElement();
            //设置MapperBean的接口名
            String namespace = root.attributeValue("namespace").trim();
            mapperBean.setInterfaceName(namespace);
            //通过遍历得到配置的所有方法
            Iterator rootIterator = root.elementIterator();
            ArrayList<Function> functions = new ArrayList<>();
            while (rootIterator.hasNext()) {
                Element element = (Element)rootIterator.next();
                Function function = new Function();

                String sqlType = element.getName().trim();
                String funcName = element.attributeValue("id").trim();
                String resultType = element.attributeValue("resultTpye").trim();
                String sql = element.getTextTrim();

                function.setFuncName(funcName);
                function.setResultType(Class.forName(resultType).newInstance());
                function.setSqlType(sqlType);
                function.setSql(sql);

                functions.add(function);
            }
            //将结果集合添加到MapperBean实例中
            mapperBean.setFunctions(functions);
        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return mapperBean;
    }
}
