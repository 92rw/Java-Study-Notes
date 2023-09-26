package com.javaweb.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Dom4jExample {
    /**
     * 演示xml文件的加载
     */
    @Test
    public void loadXML() throws DocumentException {
        //得到解析器
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/students.xml"));

        System.out.println(document);
        /*通过断点Debug，可以知道这个document得到了XML DOM，其rootElement->content->elementData(Object数组)的组成是
                              rootElement
                                   |
                   --------------------------------
                   |      |       |        |      |
                换行符 student01 换行符 student02 换行符
                          |                |
                        各节点           各节点
           子节点的结构与父节点类似，换行符也是其中的构成元素
        */
        //return document;
    }

    /**
     * 遍历得到所有student信息
     */
    @Test
    public void listStudents() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/students.xml"));

        //得到rootElement（上面的方法已经验证过这个rootElement的组成）
        Element rootElement = document.getRootElement();
        List<Element> students = rootElement.elements("student");
        for (Element student : students) {//student就是XML文件中的student节点
            //获取Student元素的name
            Element name = student.element("name");
            Element age = student.element("age");
            Element resume = student.element("resume");
            Element gender = student.element("gender");
            System.out.printf("学生信息：name= %s age= %s resume= %s gender= %s\n",
                    name.getText(),age.getText(),resume.getText(),gender.getText());
        }
    }

    /**
     * 获取某个特定学生的信息
     */
    @Test
    public void readSpecificStudent() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/students.xml"));

        Element rootElement = document.getRootElement();
        //获取第一个学生发信息
        //get方法得到的是Object类对象，需要向下转型为Element类型
        Element student = (Element) rootElement.elements("student").get(0);
        //此处获取信息与上面迭代的代码块一致
        Element name = student.element("name");
        Element age = student.element("age");
        Element resume = student.element("resume");
        Element gender = student.element("gender");
        System.out.printf("学生信息：name= %s age= %s resume= %s gender= %s\n",
                name.getText(),age.getText(),resume.getText(),gender.getText());

        //获取student元素的属性，不使用element方法
        System.out.println("id= " + student.attributeValue("id"));
    }
    /**
     * 加元素(要求: 添加一个学生到xml中) [不要求，使用少，了解]
     * @throws Exception
     */
    @Test
    public void add() throws Exception {

        //1.得到解析器
        SAXReader saxReader = new SAXReader();
        //2.指定解析哪个xml文件
        Document document = saxReader.read(new File("src/students.xml"));


        //首先创建一个学生节点对象
        Element newStu = DocumentHelper.createElement("student");//方法和HTML DOM类似

        //如何给元素添加属性
        newStu.addAttribute("id", "04");
        Element newStu_name = DocumentHelper.createElement("name");
        newStu_name.setText("宋江");
        //创建age元素
        Element newStu_age = DocumentHelper.createElement("age");
        newStu_age.setText("23");
        //创建resume元素
        Element newStu_intro = DocumentHelper.createElement("resume");
        newStu_intro.setText("梁山老大");

        //把三个子元素（节点）加到 newStu下
        newStu.add(newStu_name);
        newStu.add(newStu_age);
        newStu.add(newStu_intro);
        //再把newStu节点加到根元素
        document.getRootElement().add(newStu);
        //直接输出会出现中文乱码:
        OutputFormat output = OutputFormat.createPrettyPrint();
        output.setEncoding("utf-8");//输出的编码utf-8

        //把我们的xml文件更新
        // lets write to a file
        //new FileOutputStream(new File("src/myClass.xml"))
        //使用到io编程 FileOutputStream 就是文件字节输出流
        XMLWriter writer = new XMLWriter(
                new FileOutputStream(new File("src/students.xml")), output);
        writer.write(document);
        writer.close();

    }

    /**
     * //删除元素(要求：删除第一个学生) 使用少，了解
     * @throws Exception
     */
    @Test
    public void del() throws Exception {
        //1.得到解析器
        SAXReader saxReader = new SAXReader();
        //2.指定解析哪个xml文件
        Document document = saxReader.read(new File("src/students.xml"));
        //找到第三个学生（索引值为2）
        Element stu = (Element) document.getRootElement().elements("student").get(2);
        //删除该学生：找到父节点，然后删除连接关系
        stu.getParent().remove(stu);
//        删除该学生的某个属性
//        stu.remove(stu.attribute("id"));//删除id

        //更新xml
        //直接输出会出现中文乱码:
        OutputFormat output = OutputFormat.createPrettyPrint();
        output.setEncoding("utf-8");//输出的编码utf-8
        //把我们的xml文件更新
        XMLWriter writer = new XMLWriter(
                new FileOutputStream(new File("src/students.xml")), output);
        writer.write(document);
        writer.close();
        System.out.println("删除成功~");
    }


    /**
     * 更新元素(要求把所有学生的年龄+3) 使用少，了解
     * @throws Exception
     */
    @Test
    public void update() throws Exception {

        //1.得到解析器
        SAXReader saxReader = new SAXReader();
        //2.指定解析哪个xml文件
        Document document = saxReader.read(new File("src/students.xml"));
        //得到所有学生的年龄
        List<Element> students = document.getRootElement().elements("student");
        //遍历, 所有的学生元素的age+3
        for (Element student : students) {
            //取出年龄
            Element age = student.element("age");
            age.setText((Integer.parseInt(age.getText()) + 3) + "");
        }

        //更新
        //直接输出会出现中文乱码:
        OutputFormat output = OutputFormat.createPrettyPrint();
        output.setEncoding("utf-8");//输出的编码utf-8

        //把我们的xml文件更新
        XMLWriter writer = new XMLWriter(
                new FileOutputStream(new File("web/test/students.xml")), output);
        writer.write(document);
        writer.close();
        System.out.println("更新成功~");
    }
}

/**
 * 利用xml文件中的train元素，创建StoppedTrain对象
 * 解决方式：遍历xml文件中的元素，根据元素信息创建类对象
 */
class Dom4jHomework{
    public static void main(String[] args) throws DocumentException, ParseException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("web/test/trains.xml"));

        //遍历元素
        List<Element> trains = document.getRootElement().elements("train");
        for (Element train : trains) {
            //取出train元素所有信息
            Element start_time = train.element("start_time");
            Element end_time = train.element("end_time");
            Element distance = train.element("distance");
            Element hardseatprice = train.element("hardseatprice");
            //取出属性
            String number = train.attributeValue("number");
            //创建成StoppedTrain对象
            StoppedTrain stoppedTrain = new StoppedTrain();
            //属性的值本身就是String类型
            stoppedTrain.setNumber(number);
            //子元素需要调用方法才能获取具体值
            stoppedTrain.setStart_time(start_time.getText());
            //字符串转时间使用SimpleDateFormat类时，需要抛出异常
            stoppedTrain.setEnd_time(new SimpleDateFormat("yyyyMMdd").parse(end_time.getText()));
            //字符串转整数型
            stoppedTrain.setDistance(Integer.parseInt(distance.getText()));
            //字符串转小数
            stoppedTrain.setHardseatprice(Double.parseDouble(hardseatprice.getText()));
            System.out.println(stoppedTrain);
        }
    }
}

class StoppedTrain{
    private String number;
    private String start_time;
    private Date end_time;
    private Integer distance;
    private Double hardseatprice;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getHardseatprice() {
        return hardseatprice;
    }

    public void setHardseatprice(Double hardseatprice) {
        this.hardseatprice = hardseatprice;
    }

    @Override
    public String toString() {
        return "停运列车{" +
                "车次='" + number + '\'' +
                ", 开行时间='" + start_time + '\'' +
                ", 停运日期=" + new SimpleDateFormat("yyyy年MM月dd日").format(end_time) +
                ", 运行里程=" + distance +
                "km, 全程硬座票价=" + hardseatprice +
                "元}";
    }
}