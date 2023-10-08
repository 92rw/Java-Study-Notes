# MyBatis

（简化数据库操作的持久层框架）

中文手册 [mybatis – MyBatis 3 | 简介](https://mybatis.org/mybatis-3/zh/index.html)

Maven中央仓库 [Maven Repository: Search/Browse/Explore](https://mvnrepository.com/)，用于查找项目引入包的依赖文件



Java操作DB分析

| Java程序操作          | 存在的问题                                       | MyBatis方式             |
| --------------------- | ------------------------------------------------ | ----------------------- |
| 连接数据库/连接池     | 连接数据库的代码由不同程序员编写（存在语法差异） | 只需要配置即可          |
| 编写SQL语句           | 程序不是OOP的方式操作DB（希望以对象的形式操作）  | OOP的方式操作DB         |
| 发送SQL语句，完成crud | 在程序/代码层面，SQL语句硬编码，没有解耦         | 写在xml文件中，实现解耦 |

MyBatis是一个持久层框架

* 前身是ibatis，在ibatis3.x时，更名为MyBatis
* MyBatis在java和sql之间提供更灵活的映射方案
* mybatis可以将对数据表的操作（sql方法）等等直接剥离，写到xml配置文件，实现和iava代码的解耦
* mybatis可以通过SQL操作DB，但是建库建表的工作需要程序员完成



Java以MyBatis方式操作DB的组件和工作机制

Java操作DB

* JavaBean 类- POJO
* XX(JavaBean)Mapper.java接口：声明方法crud，（1）方法实现可以是注解方式，（2）方法也可以在对应的 XXMapper.xml 去完成
* 操作DB中的train表：①先创建 Train 对象，②通过MyBatis的 mybatis-config 文件获取到 SessionFactory 对象（可以理解成是连接池），③通过 SessionFactory 对象获取到SqlSession（可以理解成是连接），④获取到 XXMapper 的对象，⑤调用 XXMapper 的方法，完成对数据库表各种操作

MyBatis框架，本质是jar包

* mybatis-config.xml：1.配置数据库连接/数据源 2.管理XXMapper.xml
* XXMapper.xml（比如 StationMapper.xml)：配置SQL，完成对 station 表的 crud
* 连接池



## 快速入门

要求：开发一个MyBatis项目，通过MyBatis的方式可以完成对 station 表的crud操作

代码实现：

1. 在 MySQL 中创建 mybatis 数据库，并添加 station 表

   ```mysql
   CREATE DATABASE `mybatis`;
   CREATE TABLE `station` (
       `id` INT NOT NULL AUTO_INCREMENT,
       `name` VARCHAR(10) NOT NULL,
       `code` INT NOT NULL,
       `cargo` TINYINT NOT NULL,#0表示不办理货运，1表示办理货运
       `openday` DATE DEFAULT NULL,
       `distance` DOUBLE NOT NULL,
       PRIMARY KEY (`id`)
   ) CHARSET=utf8
   ```

2. 创建Maven项目：新建Project作为父项目/工程，删除src目录后创建新的Module作为子项目，这样子项目可以从父项目的 pom.xml 引入依赖，这样开发简单、提高复用性且便于管理。此时父项目的完整坐标 `groupId（组织名）+artifactId（项目名）`。在 pom.xml 文件中加入依赖

   ```xml
       <dependencies>
           <!--MySQL依赖-->
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.24</version>
           </dependency>
           <!--MyBatis依赖-->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.5.13</version>
           </dependency>
           <!--Junit依赖-->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.11</version>
               <!--这里的scope表示jar的作用范围在test目录-->
               <!--<scope>test</scope>-->
           </dependency>
       </dependencies>
   ```

3. 创建子项目：在父项目右键→New→Module（命名为mybatis-quickstart），创建后在父项目的 pom.xml 中增加了 `<packaging>pom</packaging>` 语句，表示父项目以多个子模块/子项目管理工程；在`<modules>` 标签详细指定了管理的子模块 。子项目的 pom.xml 的 `<parent>` 标签指定了父项目的坐标，其 groupId 和父项目保持一致

4. resources目录新建配置文件 src/main/resources/mybatis-config.xml，从官方文档获取核心设置的模板并根据实际修改

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "https://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <environments default="development">
           <environment id="development">
               <!--配置事务管理器-->
               <transactionManager type="JDBC"/>
               <!--配置数据源-->
               <dataSource type="POOLED">
                   <!--配置驱动-->
                   <property name="driver" value="com.mysql.jdbc.Driver"/>
                   <!--配置连接MySQL的协议、IP地址端口、连接的DB、使用安全连接、指定格式防止编码错误-->
                   <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                   <property name="username" value=MySQL用户名/>
                   <property name="password" value=MySQL密码/>
               </dataSource>
           </environment>
       </environments>
       <!--配置需要管理的 XXMapper.xml 文件，注意全路径使用"/"连接符-->
       <!--可通过拷贝Path from Source Root来获取路径-->
       <mappers>
           <mapper resource="exercise/mapper/StationMapper.xml"/>
       </mappers>
   </configuration>
   
   ```

5. 创建JavaBean类 src/main/java/exercise/entity/Station.java

   ```java
   public class Station {
       private Integer id;
       private String name;
       private Integer code;
       private Integer cargo;
       private Date openday;
       private double distance;
       //无参和全参构造器，Getter和Setter方法，toString方法
   }
   
   ```

6. 新建 Java 接口 src/main/java/exercise/mapper/StationMapper.java 声明操作数据库表的方法，通过注解或xml文件实现

   ```java
   public interface StationMapper {
       void addStation(Station station);
       void delStation(Integer id);
       void updateStation(Station station);
       Station getStationById(Integer id);
       List<Station> findAllStations();
   }
   ```

7. 在相同目录新建xml文件 src/main/java/exercise/mapper/StationMapper.xml，从官方文档获取XML映射文件的模板并修改

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--namespace指定对应的接口-->
   <mapper namespace="exercise.mapper.StationMapper">
   
       <!--配置的id就是接口方法名，parameterType表示放入形参类型，可以用简单类名-->
       <insert id="addStation" parameterType="exercise.entity.Station" useGeneratedKeys="true" keyProperty="id">
           <!--写入SQL语句，可以先在navicat测试后再添加-->
           INSERT INTO `station`
           (`name`, `code`, `cargo`, `openday`, `distance`)
           VALUES (#{name}, #{code}, #{cargo}, #{openday}, #{distance})
       </insert>
   
       <!--Integer是java包装类，可以使用简写-->
       <delete id="delStation" parameterType="Integer">
           DELETE FROM `station` WHERE id = #{id}
       </delete>
   
       <update id="updateStation" parameterType="exercise.entity.Station">
           UPDATE `station`
           SET `name` = #{name}, `code` = #{code}, `cargo` = #{cargo}, `openday` = #{openday}, `distance` = #{distance}
           WHERE `id` = #{id}
       </update>
       
       <select id="getStationById" parameterType="Station">
           SELECT * FROM `station` WHERE `id` = #{id}
       </select>
   
       <!--根据官方文档：如果返回的是集合，应当配置为“集合包含的类型”，而不是集合本身类型-->
       <select id="findAllStations" parameterType="Station">
           SELECT * FROM `station`
       </select>
   </mapper>
   ```

8. 编写工具类 src/main/java/exercise/utils/MyBatisUtils.java，作用是通过SessionFactory 对象获取到SqlSession

   ```java
   public class MyBatisUtils {
       private static SqlSessionFactory sqlSessionFactory;
       //在静态代码块完成静态属性的初始化
       static {
           try {
               //指定配置文件（src/resource目录，在编译后即target/classes目录）
               String resource = "mybatis-config.xml";
               //此处使用的Resources类来自 org.apache.ibatis.io 包
               InputStream resourceAsStream = Resources.getResourceAsStream(resource);
               //这里获得的是 org.apache.ibatis.session.defaults.DefaultSqlSessionFactory 对象
               sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   
       //编写方法，返回SqlSession对象
       public static SqlSession getSqlSession() {
           return sqlSessionFactory.openSession();
       }
   }
   
   ```

9. 在父工程的 pom.xml 文件中增加 build 配置，解决资源导出失败问题

   ```xml
       <!--在build中配置resources，将src目录下的xml文件导出到对应的target目录下-->
       <build>
           <resources>
               <resource>
                   <directory>src/main/java</directory>
                   <includes>
                       <include>**/*.xml</include>
                   </includes>
               </resource>
               <resource>
                   <directory>src/main/resources</directory>
                   <includes>
                       <include>**/*.xml</include>
                       <include>**/*.properties</include>
                   </includes>
               </resource>
           </resources>
       </build>
   ```

10. 在测试目录下新建测试类 src/test/java/quickstart/StationMapperTest.java

    ```java
    public class StationMapperTest {
        private SqlSession sqlSession;
        private StationMapper stationMapper;
    
        //编写方法完成初始化
        //@Before注解表示在执行目标测试方法前，会先执行该方法
        @Before
        public void init() {
            sqlSession = MyBatisUtils.getSqlSession();
            //底层使用动态代理，获取到 com.sun.proxy.$Proxy7 对象
            stationMapper = sqlSession.getMapper(StationMapper.class);
        }
    
        @Test
        public void addStation(){
            Station qdk = new Station();
            qdk.setName("青岛");
            qdk.setCode(18089);
            qdk.setCargo(1);
            qdk.setOpenday(new Date(1, 3, 8));
            qdk.setDistance(0.63);
            stationMapper.addStation(qdk);
            System.out.println("添加车站" + qdk);
            System.out.println("在数据库中的id值" + qdk.getId());
    
            //如果是增删改，需要提交事务
            if (sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
            System.out.println("添加成功");
        }
    
        @Test
        public void delStation(){
            stationMapper.delStation(2);
            //如果是增删改，需要提交事务
            if (sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
            System.out.println("删除成功");
        }
    
        @Test
        public void updateStation() {
            Station qzn = new Station();
            qzn.setName("青州市南");
            qzn.setCode(17882);
            qzn.setCargo(1);
            qzn.setOpenday(new Date(3, 3, 12));
            qzn.setDistance(235.966);
    
            qzn.setId(3);//用于替换既有项
            stationMapper.updateStation(qzn);
    
            //如果是增删改，需要提交事务
            if (sqlSession != null) {
                sqlSession.commit();
                sqlSession.close();
            }
            System.out.println("修改成功");
        }
    
        @Test
        public void getStationById() {
            Station stationById = stationMapper.getStationById(3);
            System.out.println(stationById);
    
            if (sqlSession != null) {
                sqlSession.close();
            }
            System.out.println("查询完成");
        }
    
        @Test
        public void findAllStations() {
            List<Station> allStations = stationMapper.findAllStations();
            for (Station station : allStations) {
                System.out.println(station);
            }
            if (sqlSession != null) {
                sqlSession.close();
            }
            System.out.println("查询完成");
        }
    }
    
    ```

11. 测试程序运行失败的解决方法：File→Settings→Build,Execution, Deployment→Build Tools→Maven，修改 Maven home directory 后在 Maven 程序的 Lifecycle 中 clean

    


## 日志输出

相关说明：

* [mybatis – MyBatis 3 | 日志](https://mybatis.org/mybatis-3/zh/logging.html)
* [mybatis – MyBatis 3 | 配置](https://mybatis.org/mybatis-3/zh/configuration.html#settings)

案例演示

1. 修改 mybatis-config.xml 文件，在 \<configuration> 标签加入日志输出配置，方便分析SQL语句（注意需要放在最前面）

   ```xml
       <!--配置MyBatis自带的日志输出-->
       <settings>
           <setting name="logImpl" value="STDOUT_LOGGING"/>
       </settings>
   ```

2. 以StationMapperTest的updateStation方法为例，会在控制台输出程序的操作流程





