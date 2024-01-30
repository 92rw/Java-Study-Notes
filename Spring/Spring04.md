# Spring配置Bean（基于xml文件）

### ⑬bean配置信息复用

在Spring的IOC容器，通过了一种继承的方式来实现bean配置信息的重用

```xml
<bean class="com.exercise.spring.bean.Monster" id="monster01">
    <property name="momsterId" value="4"/>
    <property name="name" value="公孙胜"/>
    <property name="skill" value="入云龙"/>
</bean>
```

当 xml 文件中已有上述数据，需要复用时，可以通过继承的方式获取

```xml
<bean class="com.exercise.spring.bean.Monster" id="monster07" parent="monster01"/>
```

此时父bean也可以用 `abstruct="true"` 修饰，表示这个bean专门用于继承，不能被获取实例化



### ⑭通过属性文件给bean赋值

案例演示：

1. 在src目录下新建properties文件：注意中文字符需要先转为unicode编码

   ```pro
   monsterId=76
   name=\u5b8b\u6e05
   skill=\u94c1\u6247\u5b50
   ```

2. 在 beans02.xml 文件中链接创建的properties文件（需要导入名字空间Create namespace declaration）

   ```xml
   <context:property-placeholder location="classpath:my.properties"/>
   ```

3. 配置beans对象，属性值可用 ${properties文件配置的属性值}代替

   ```xml
   <bean class="com.exercise.spring.bean.Monster" id="monster09">
       <property name="momsterId" value="${monsterId}"/>
       <property name="name" value="${name}"/>
       <property name="skill" value="${skill}"/>
   </bean>
   ```

4. 编写测试代码调试

   ```java
   @Test
   public void getBeanByProperties() {
       ApplicationContext ioc = new ClassPathXmlApplicationContext("beans02.xml");
       Monster iocBean = ioc.getBean("monster09",Monster.class);
   
       System.out.println(iocBean);
   }
   ```

   

### ⑮基于XML的bean自动装配

可用于分层调用

|          | Controller层                 | Service层                | DAO层       |
| -------- | ---------------------------- | ------------------------ | ----------- |
| 类文件   | OrderAction                  | OrderService             | OrderDAO    |
| 私有属性 | OrderService                 | OrderDAO                 |             |
| 配置方法 | OrderService的getter和setter | OrderDAO的getter和setter | saveOrder() |

以 OrderService 层的配置为例，

* 传统方法，在配置 beans.xml 文件时，在 OrderService 的 \<bean> 标签部署子标签，最终效果

  ```xml
  <bean class="com.exercise.spring.service.OrderService" id="orderService">
        <property name="orderDAO" ref="orderDAO"/>
  </bean>
  ```

* 自动装配，仅需要一行代码

  ```xml
  <bean autowire="byType" class="com.exercise.spring.service.OrderService" id="orderService">
  ```



代码解读：

* autowire="byType" 表示在创建 orderService 时，通过类型的方式给对象属性自动完成赋值
* 容器会自动查找是否存在 OrderDAO 类型的对象，如果存在就会自动装配，要求容器中不能有两个及以上 OrderDAO 类型的对象
* autowire="byName" 表示在创建 orderService 时，根据属性的 setXxx() 方法来找对象的id，如果没有就装配失败（底层是反射机制）
* OrderDAO 对象没有属性，没有必要配置 autowire



### ⑯Spring EL表达式[仅作演示]

1. SpringExpressionLanguage，Spring 表达式语言，简称 SpEL。支持运行时查询并可以操作对象。
2. 和 EL 表达式一样，SpEL 根据 JavaBean 风格的 getxxx、setxxx 方法定义的属性访问对象
3. SpEL 使用 #{...} 作为定界符，所有在大框号中的字符都将被认为是 SpEL 表达式。

案例演示

1. 配置一个 SpELBean 类，配置以下属性和方法，并配置 getter、setter方法、无参构造器和toString方法

   ```java
   private String name;
   private Monster monster;
   private String monsterName;
   private String crySound; //叫声
   private String bookName;
   private Double result;
   
   //cry 方法会返回字符串
   public String cry(String sound) {
       return "发出 " + sound + "叫声...";
   }
   
   //read 返回字符串
   public static String read(String bookName) {
       return "正在看 " + bookName;
   }
   ```

2. 在对应的xml文件的 \<beans> 标签配置

   ```xml
   <!--配置一个monster对象-->
   <bean id="monster10" class="com.exercise.spring.bean.Monster">
       <property name="monsterId" value="88"/>
       <property name="name" value="汤隆"/>
       <property name="skill" value="金钱豹子"/>
   </bean>
   
   <!-- spring el 表达式使用-->
   <bean id="spELBean" class="com.exercise.spring.bean.SpELBean">
       <!-- sp el 给字面量 -->
       <property name="name" value="#{'SpELBean类'}"/>
       <!-- sp el 引用其它bean -->
       <property name="monster" value="#{monster01}"/>
       <!-- sp el 引用其它bean的属性值 -->
       <property name="monsterName" value="#{monster01.name}"/>
       <!-- sp el 调用普通方法，将其返回值赋给对应属性 -->
       <property name="crySound" value="#{spELBean.cry('喵喵的..')}"/>
       <!-- sp el 调用静态方法（需使用全类名），将其返回值赋给对应属性 -->
       <property name="bookName" value="#{T(com.exercise.spring.bean.SpELBean).read('春秋')}"/>
       <!-- sp el 通过运算赋值 -->
       <property name="result" value="#{89*6.4}"/>
   </bean>
   ```

3. 在测试类中进行调试

   ```java
   @Test
   public void getBeanBySpEL() {
       ApplicationContext ioc = new ClassPathXmlApplicationContext("beans02.xml");
       SpELBean iocBean = ioc.getBean("spELBean",SpELBean.class);
   
       System.out.println(iocBean);
   }
   ```

   
