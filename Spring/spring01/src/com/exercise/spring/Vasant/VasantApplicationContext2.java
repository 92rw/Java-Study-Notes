package com.exercise.spring.Vasant;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class VasantApplicationContext2 {
    private Class configClass;
    private final ConcurrentHashMap<String, Object> ioc = new ConcurrentHashMap<>();//存放反射创建的对象

    public VasantApplicationContext2(Class configClass) {
        //1. 利用反射获取注解，通过注解配置的value，得到要扫描的包
        this.configClass = configClass;
        ComponentScan annotation = (ComponentScan)this.configClass.getDeclaredAnnotation(ComponentScan.class);
        String packagePath = annotation.value();

        //2. 替换路径格式，得到绝对路径。注意String类型需要重新赋值引用
        packagePath = packagePath.replace(".","/");
        ClassLoader classLoader = configClass.getClassLoader();
        URL resource = classLoader.getResource(packagePath);

        //3. 得到要扫描的包内资源（.class）文件，按条件进行实例化
        File path = new File(resource.getFile());
        if (path.isDirectory()) {
            // 正式的Spring框架内，会使用递归方式获取子文件夹内容，此处做简化
            File[] files = path.listFiles();
            for (File file : files) {
                String absolutePath = file.getAbsolutePath();
                //System.out.println(absolutePath);

                // 考虑到目录下可能有其他文件，需要对文件名称进行过滤
                if (absolutePath.endsWith(".class")) {
                    //1) 得到类名，要从斜杠后面的1个字符开始截取（substring() 方法左闭右开）
                    String className = absolutePath.substring(
                            absolutePath.lastIndexOf("\\") + 1, absolutePath.lastIndexOf(".class"));
                    //System.out.println(className);

                    //2) 得到类的完整路径（全类名）
                    String classFullName = packagePath.replace("/",".") + "." + className;
                    //System.out.println(classFullName);

                    //3) 判断该类是否需要注入容器中（检查是否存在注解）：此处使用两种类加载方式
                    //前者使用类加载器避免加载静态方法节省资源，后者创建完整的类实例
                    try {
                        Class<?> aClass = classLoader.loadClass(classFullName);
                        if (aClass.isAnnotationPresent(Component.class) ||
                            aClass.isAnnotationPresent(Controller.class) ||
                            aClass.isAnnotationPresent(Service.class) ||
                            aClass.isAnnotationPresent(Repository.class)) {

                            if (aClass.isAnnotationPresent(Component.class)) {
                                //一个类可能配置了多个注解，因此需要专门提取出来
                                Component component = aClass.getDeclaredAnnotation(Component.class);
                                String id = component.value();
                                if (!"".endsWith(id)) {//默认是空字符串，因此可以用这种方式判断
                                    className = id;//替换
                                }
                            }
                            //若存在对应的注解，则说明该类可反射获取实例并加入容器
                            Class<?> clazz = Class.forName(classFullName);
                            Object instance = clazz.newInstance();

                            //利用 StringUtils 类，完成首字母小写
                            ioc.put(StringUtils.uncapitalize(className), instance);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (String s : ioc.keySet()) {
            System.out.println(s);
            System.out.println(ioc.get(s));
            System.out.println("===============");
        }
    }

    //编写方法返回容器对象
    public Object getBean(String name){
        return ioc.get(name);
    }
}
