# Spring配置Bean（基于xml文件）

### ②通过类型获取bean

配置beans.xml文件可以不指定 &lt;bean> 标签的 id

```xml
<bean class="com.exercise.spring.bean.Monster">
    <property name="momsterId" value="10"/>
    <property name="name" value="柴进"/>
    <property name="skill" value="小旋风"/>
</bean>
```

通过 Spring 的 IOC 容器，获取一个 bean 对象

```java
public void testBeanByType() {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
    //getBean(Class<T> var1)
    Monster iocBean = ioc.getBean(Monster.class);    
    //关闭连接
    ((ConfigurableApplicationContext)ioc).close();
}
```

说明：

* 要求 IOC 容器中，同一个类的 bean 只能有一个，否则会抛出 NoUniqueBeanDefinitionException 异常
* 如果bean有多个，getBean方法需要传入String 类型的 `"全类名#编号"`
* 应用场景：比如 XxxAction/Servlet/Controller 或XxxService，在一个线程中只需要一个对象实例（单例）的情况

### ③通过构造器配置bean

给JavaBean类配置有参构造器

```java
public Monster(Integer momsterId, String name, String skill) {
    this.momsterId = momsterId;
    this.name = name;
    this.skill = skill;
}
```

在beans.xml文件的配置如下

* constructor-arg 标签指定使用构造器参数
* index表示构造器的参数编号，从0开始计算

```xml
<bean class="com.exercise.spring.bean.Monster" id="monster02">
    <constructor-arg value="30" index="0"/>
    <constructor-arg value="张顺" index="1"/>
    <constructor-arg value="浪里白条" index="2"/>
</bean>
```

* 除了index，还可以通过 参数名Name 指定

```xml
<bean class="com.exercise.spring.bean.Monster" id="monster02">
    <constructor-arg value="30" name="momsterId"/>
    <constructor-arg value="张顺" name="name"/>
    <constructor-arg value="浪里白条" name="skill"/>
</bean>
```

* 除了index，还可以通过 参数类型Type 指定。因为单一个类不可能有相同的参数类型

```xml
<bean class="com.exercise.spring.bean.Monster" id="monster02">
    <constructor-arg value="30" type="java.lang.Integer"/>
    <constructor-arg value="张顺" type="java.lang.String"/>
    <constructor-arg value="浪里白条" type="java.lang.String"/>
</bean>
```

### ④通过p名称空间配置bean

第一次配置 bean.xml 文件会提示 Namespace 'p' is not bound，

点击 Create namespace declaratior 配置xmlns链接后可解决

```xml
<bean class="com.exercise.spring.bean.Monster" id="monster03"
      p:momsterId="72"
      p:name="陈达"
      p:skill="跳涧虎"
/>
```

### ⑤引用/注入 其它bean对象

在Spring的IOC容器，可以通过ref来实现bean对象的相互引用

效果：在service层中，不再需要新建dao对象作为私有属性，直接从容器中引用

* 新建 src/com/exercise/spring/service/MemberServiceImpl.java 文件和 src/com/exercise/spring/dao/MemberDAOImpl.java 文件

* 将 DAO层的对象作为Service层的私有属性，给 MemberServiceImpl 类配置getter和setter方法

* 配置beans.xml文件

  ```xml
  <!--配置dao层和service层的对象-->
  <bean class="com.exercise.spring.dao.MemberDAOImpl" id="memberDAO" />
  <bean class="com.exercise.spring.service.MemberServiceImpl" id="memberService" >
      <property name="memberDAO" ref="memberDAO"></property>
  </bean>
  <!--ref表示引用对象，这里体现出Spring容器的依赖注入-->
  <!--Spring容器中的bean对象，对xml文件的配置顺序没有要求。但是建议按顺序写，方便阅读-->
  ```

### ⑥引用/注入 内部bean对象

直接配置内部的bean对象

配置beans.xml文件时，只需要嵌套bean标签作为内部属性，其余java文件配置和上个方法一样

```xml
<!--配置service层的对象内部的bean-->
<bean class="com.exercise.spring.service.MemberServiceImpl" id="memberService2">
    <property name="memberDAO">
        <bean class="com.exercise.spring.dao.MemberDAOImpl"/>
    </property>
</bean>
```



### ⑦引用/注入 集合/数组类型

配置Master类，私有属性包括 String name（主人名），List&lt;Monster> monsterList，Map&lt;String, Monster> monsterMap，Set&lt;Monster> monsterSet，String[] monsterName，Properties pros

1. 给 List&lt;Monster> monsterList 属性赋值

   ```xml
   <bean class="com.exercise.spring.bean.Master" id="master">
       <property name="name" value="地煞星" />
       <property name="monsterList">
           <list>
               <ref bean="monster01"/>
               <ref bean="monster02"/>
               <bean class="com.exercise.spring.bean.Monster">
                   <property name="name" value="乐和" />
                   <property name="momsterId" value="77" />
                   <property name="skill" value="铁叫子" />
               </bean>
           </list>
       </property>
   </bean>
   <!--可以用引用方式载入，也可在内部新建bean-->
   ```

2. 给 Map&lt;String, Monster> monsterMap 属性赋值

   ```xml
   <bean class="com.exercise.spring.bean.Master" id="master">
       <property name="name" value="地煞星" />
       <property name="monsterMap">
           <map>
               <entry>
                   <key>
                       <value>monster1</value>
                   </key>
                   <ref bean="monster01" />
               </entry>
               <entry>
                   <key>
                       <value>monster2</value>
                   </key>
                   <ref bean="monster02" />
               </entry>
           </map>
       </property>
   </bean>
   <!--向每个entry配置key-value的值-->
   ```

3. 给 Set&lt;Monster> monsterSet 属性赋值

   ```xml
   <bean class="com.exercise.spring.bean.Master" id="master">
       <property name="name" value="地煞星" />
       <property name="monsterSet">
           <set>
               <ref bean="monster01"/>
               <ref bean="monster02"/>
               <bean class="com.exercise.spring.bean.Monster">
                   <property name="name" value="乐和" />
                   <property name="momsterId" value="77" />
                   <property name="skill" value="铁叫子" />
               </bean>
           </set>
       </property>
   </bean>
   ```

4. 给 String[] monsterName (数组)属性赋值

   ```xml
   <bean class="com.exercise.spring.bean.Master" id="master">
       <property name="name" value="地煞星" />
       <property name="monsterName">
           <array>
               <value>小磁怪</value>
               <value>小山猪</value>
               <value>小福蛋</value>
           </array>
       </property>
   </bean>
   <!--数组配置（array标签内部）的内容，取决于Java类中配置的数组类型-->
   ```

5. 给 Properties pros 属性赋值

   ```xml
   <!-- Properties是Hashtable，赋值结构是k-v，都是字符串-->
   <bean class="com.exercise.spring.bean.Master" id="master">
       <property name="name" value="地煞星" />
       <property name="pros">
           <props>
               <prop key="username">root</prop>
               <prop key="password">1026</prop>
               <prop key="ip">127.0.0.1</prop>
           </props>
       </property>
   </bean>
   ```

使用细节：主要掌握List/Map/Properties三种集合的使用

### ⑧通过util名称空间创建list

新建 BookStore 类，配置 List&lt;String> bookList属性和getter、setter方法

如果只是单个对象，对 beans.xml 文件可以直接内部配置

```xml
<bean class="com.exercise.spring.bean.BookStore" id="bookStore">
    <property name="bookList">
        <list>
            <value>红楼梦</value>
            <value>西游记</value>
            <value>水浒传</value>
            <value>三国演义</value>
        </list>
    </property>
</bean>
```

如果多个BookStore 实例都有这个bookList，可提取到外部，实现数据复用

```xml
<util:list id="greatClassics">
    <value>红楼梦</value>
    <value>西游记</value>
    <value>水浒传</value>
    <value>三国演义</value>
</util:list>

<bean class="com.exercise.spring.bean.BookStore" id="bookStore">
    <property name="bookList" ref="greatClassics">
    </property>
</bean>
```

### ⑨级联属性赋值

A对象有私有属性B，可以在创建A对象时连带内部属性B一起赋值

1. 新建 Dept 类，配置String name属性和Setter方法

2. 新建 Emp 类，配置 String name属性、Dept dept属性和Setter方法

3. 配置 beans.xml 文件

   ```xml
   <bean class="com.exercise.spring.bean.Dept" id="dept"/>
   <bean class="com.exercise.spring.bean.Emp" id="emp">
       <property name="name" value="一丈青"/>
       <property name="dept" ref="dept"/>
       <property name="dept.name" value="地煞星"/>
   </bean>
   ```

   

### ⑩通过静态工厂获取对象

1. 配置静态工厂类StaticFactory

   ```java
   package com.exercise.spring.factory;
   
   import com.exercise.spring.bean.Monster;
   import java.util.HashMap;
   import java.util.Map;
   
   public class StaticFactory {
       private static Map<String, Monster> monsterMap;
   
       //使用静态代码块进行初始化
       static {
           monsterMap = new HashMap<>();
           monsterMap.put("monster04", new Monster(68, "童威","出洞蛟"));
           monsterMap.put("monster05", new Monster(69, "童猛","翻江蜃"));
       }
   
       public static Monster getMonster(String key) {
           return monsterMap.get(key);
       }
   }
   ```

2. 配置 beans.xml 文件

   ```xml
   <bean id="staticFactory" class="com.exercise.spring.factory.StaticFactory" factory-method="getMonster">
       <constructor-arg value="monster04"/>
   </bean>
   <!--配置的class是静态工厂类的全路径，factory-method指定获取bean实例的方法
   constructor-arg value指定需要获取的对象实例-->
   ```

3. 说明：通过 staticFactory 调用

### ⑪通过实例工厂获取对象

1. 配置实例工厂类InstanceFactory

   ```java
   package com.exercise.spring.factory;
   
   import com.exercise.spring.bean.Monster;
   
   import java.util.HashMap;
   import java.util.Map;
   
   public class InstanceFactory {
       private Map<String, Monster> monsterMap;
   
       //使用普通代码块进行初始化
       {
           monsterMap = new HashMap<>();
           monsterMap.put("monster04", new Monster(68, "童威","出洞蛟"));
           monsterMap.put("monster05", new Monster(69, "童猛","翻江蜃"));
       }
   
       public Monster getMonster(String key) {
           return monsterMap.get(key);
       }
   }
   
   ```

2. 配置 beans.xml 文件

   ```xml
   <bean id="instanceFactory" class="com.exercise.spring.factory.InstanceFactory"/>
   <bean id="instanceFactoryObject" factory-bean="instanceFactory" factory-method="getMonster">
       <constructor-arg value="monster04"/>
   </bean>
   <!--factory-bean指定返回bean的实例工厂，constructor-arg value指定需要获取的对象实例-->
   ```

3. 说明：通过 instanceFactoryObject 调用，不同 factory-bean 获取到的bean对象不同，相同 factory-bean 获取到的 bean对象相同

### ⑫通过FactoryBean获取对象（重点）

1. 配置FactoryBean类InstanceFactory

   ```java
   package com.exercise.spring.factory;
   
   import com.exercise.spring.bean.Monster;
   import org.springframework.beans.factory.FactoryBean;
   
   import java.util.HashMap;
   import java.util.Map;
   
   public class MyFactoryBean implements FactoryBean<Monster> {
       private String key;//外部类通过key获取对应的bean对象
       private Map<String, Monster> monsterMap;
   
       //使用普通代码块进行初始化
       {
           monsterMap = new HashMap<>();
           monsterMap.put("monster06", new Monster(64, "项充","八臂哪吒"));
           monsterMap.put("monster07", new Monster(65, "李衮","飞天大圣"));
       }
   
       public void setKey(String key) {
           this.key = key;
       }
   
       @Override
       public Monster getObject() throws Exception {
           return monsterMap.get(key);
       }
   
       @Override
       public Class<?> getObjectType() {
           return Monster.class;
       }
   
       @Override
       public boolean isSingleton() {//指定返回的是否为单例
           return true;//默认是false，此处设置为true
       }
   }
   
   ```

2. 配置 beans.xml 文件

   ```xml
   <bean id="factoryBean" class="com.exercise.spring.factory.MyFactoryBean">
       <property name="key" value="monster06"/>
   </bean>
   ```

3. 说明：通过 factoryBean 调用。不同 factory-bean 获取到的bean对象不同，相同 factory-bean 获取到的 bean对象相同

