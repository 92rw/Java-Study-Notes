# XML文件配置

## bean创建顺序

案例演示1

1. 在spring的ioc容器，默认是按照配置的顺序创建bean对象

   ```xml
   <bean id="student01" class="com.exercise.bean.student"/>
   <bean id="department01" class="com.exercise.bean.Department"/>
   ```

   在容器中会先创建 student01 对象，再创建 department01 对象

2. 如果配置为

   ```xml
   <bean id="student01" class="com.exercise.bean.student" depends-on="department01"/>
   <bean id="department01" class="com.exercise.bean.Department"/>
   ```

   会先创建 department01 对象，再创建 student01 对象



案例演示2

引用/注入其它bean对象配置 Bean的过程中，beans.xml 文件的配置如下

```xml
<bean class="com.exercise.spring.dao.MemberDAOImpl" id="memberDAO" />
<bean class="com.exercise.spring.service.MemberServiceImpl" id="memberService" >
    <property name="memberDAO" ref="memberDAO"></property>
</bean>
```

程序的执行顺序为：

1. 创建 id = "memberDAO"
2. 创建 id = "memberService"
3. 调用 MemberServiceImpl.setMemberDAO() 方法，完成引用

如果在配置文件中，把两个的顺序调换，那么程序的执行顺序为：

1. 创建 id = "memberService"
2. 创建 id = "memberDAO"
3. 调用 MemberServiceImpl.setMemberDAO() 方法，完成引用



## Bean的单例和多例

在spring的ioc容器，在默认是按照单例创建的，即配置一个bean对象后，ioc容器只会创建一个bean实例。
如果,我们希望ioc容器配置的某个bean对象，是以多个则可以通过配置``
来指定

配置 \<bean> 时，默认属性为`scope="singleton"`，当程序员执行 getBean()方法时，返回的是同一个对象

如果希望每次返回一个实例形式创建的新对象，需要配置 `scope="prototype"`

* 在声明类时加入注解 `@Scope(value = "prototype")`
* 在xml文件配置属性 \<bean scope="prototype">

使用细节

* 对于单例 singleton，在启动容器时，默认就会创建，并放入 singletObject 集合中
* 当 \<bean scope="prototype"> 设置为多实例机制后，该 bean 是在 getBean() 方法调用时才创建
* 如果是单例singleton，同时希望在getBean时才创建，可以指定懒加载 lazy-init="true"（默认是false）
* 如果配置 \<bean scope="singleton" lazy-init="true"> ，那么将不会在启动容器时创建实例
* 如果配置 \<bean scope="prototype"> ，那么不管 lazy-init 如何设置，都只在 getBean() 方法调用时才创建
* 通常情况下，lazy-init就使用默认值false，在开发看来，用空间换时间是值得的，除非有特殊要求



## Bean 的生命周期

bean对象创建是由 JVM 完成的，然后执行如下方法

1. 执行构造器
2. 执行set相关方法
3. 调用bean的初始化的方法（需要在bean标签中配置 init-method）
4. 使用bean
5. 当容器关闭时候，调用bean的销毁方法（需要在bean标签中配置 destroy-method）

使用细节

1. 初始化init方法和destory方法，是程序员来指定
2. 销毁方法就是当关闭容器时，才会被调用



## Bean的后置处理器

1. 在spring的 IOC 容器，可以配置bean的后置处理器
2. 该处理器会在bean初始化方法调用前和初始化方法调用后被调用
3. 程序员可以在后置处理器中编写自己的代码

配置后置处理器的流程：

* 实现 BeanPostProcessor 接口，重写 postProcessBeforeInitialization(Object bean, String beanName) 和 postProcessAfterInitialization(Object bean, String beanName) 方法
* 两个方法传入的参数分别是IOC容器中创建/配置的bean对象和id，返回的Object是处理后的bean对象

案例演示：

1. 另外配置 beans02.xml 文件（防止多个实例对象的构造方法互相影响），\<beans> 标签配置的内容如下

   ```xml
       <bean class="com.exercise.spring.bean.Monster" id="monster08"
       init-method="init" destroy-method="destroy">
           <property name="momsterId" value="74"/>
           <property name="name" value="郑天寿"/>
           <property name="skill" value="白面郎君"/>
       </bean>
   
       <!--该后置处理器，会自动作用在容器创建的Bean对象上-->
       <bean class="com.exercise.spring.bean.MyBeanPostProcessor" id="myBeanPostProcessor"/>
   ```

2. 在Monster的JavaBean类中，增加初始化和销毁方法（可在构造方法、Setter方法中也增加输出的语句）

   ```java
   public void init(){
       System.out.println("调用初始化 init()方法");
   }
   public void destroy(){
       System.out.println("调用销毁 destroy()方法");
   }
   ```

3. 配置MyBeanPostProcessor类

   ```java
   public class MyBeanPostProcessor implements BeanPostProcessor {
       @Override
       public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
           System.out.println("调用 postProcessBeforeInitialization 方法，bean对象实例：" + bean + "bean对象的id：" + beanName);
           return null;
       }
   
       @Override
       public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
           System.out.println("调用 postProcessAfterInitialization 方法，bean对象实例：" + bean + "bean对象的id：" + beanName);
           return null;
       }
   }
   ```

4. 在测试类中调用相关方法，查看具体的调用顺序

   ```java
   @Test
   public void showLifeCircle() {
       ApplicationContext ioc = new ClassPathXmlApplicationContext("beans02.xml");
       Monster iocBean = ioc.getBean("monster08",Monster.class);
   
       System.out.println("测试方法获得的实例对象：" + iocBean);
       ((ConfigurableApplicationContext)ioc).close();
   }
   ```

5. 根据控制台输出的结果，具体的调用顺序是：构造方法、Setter方法、postProcessBeforeInitialization、init()、postProcessAfterInitialization、destroy()

说明：

* 方法的执行原理：AOP机制（反射+动态代理+IO+容器+注解）

* 作用：对IOC容器内所有对象进行统一处理，比如日志处理、权限校验、安全验证、事务管理

* 后置处理器针对所有对象编程，对多个对象进行处理→切面编程

  



