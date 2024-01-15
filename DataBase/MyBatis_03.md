# 实现MyBatis的底层机制（二）

## 实现SqlSession

说明：在原生的MyBatis中，传入 DefaultSqlSession 类CRUD方法中的String对象，不是`待执行的SQL语句`，而是`要执行的接口方法`，通过PreparedStatement完成查询。此处做了简化

1. 新建 src/main/java/exercise/mainbatis/sqlsession/SQLSession.java 文件，封装Configuration（连接）和Executor（JDBC操作）

   ```java
   package exercise.mainbatis.sqlsession;
   
   public class SQLSession {
       private Executor executor = new MyExecutor();
       private Configuration configuration = new Configuration();
   
       //原生MyBatis在此处实现了很多方法，此处仅列举一个做代码样例，且做了简化
       public <T> T selectOne(String statement, Object parameter) {
           return executor.query(statement, parameter);
       }
   }
   
   ```

2. 在 MainBatisTest 类编写测试方法

   ```java
       @Test
       public void selectOne() {
           SQLSession sqlSession = new SQLSession();
           Station selected = sqlSession.selectOne("SELECT * FROM `station` WHERE id = ?", 1);
           System.out.println("SqlSession 查询结果：" + selected);
       }
   ```



## Mapper接口和配置实现文件

Mapper接口声明方法，对应的Mapper.xml文件配置接口的方法

1. 新建接口文件 src/main/java/exercise/mapper/StationMapper.java

   ```java
   package exercise.mapper;
   
   import exercise.entity.Station;
   
   public interface StationMapper {
       Station getStationById(Integer id);
   }
   
   ```

2. 在resources目录下新建 StationMapper.xml 文件（此路径进行简化，便于类加载器直接调用）

   ```xml
   <?xml version="1.0" encoding="utf-8" ?>
   <mapper namespace="exercise.mapper.StationMapper">
       <select id="getStationById" resultTpye="exercise.entity.Station">
           SELECT * FROM station WHERE id = ?
       </select>
   </mapper>
   ```

3. 新建 src/main/java/exercise/mainbatis/config/Function.java 文件，用于存储扫描到的xml文件信息

   ```java
   package exercise.mainbatis.config;
   
   import lombok.Getter;
   import lombok.Setter;
   
   @Getter
   @Setter
   public class Function {
       private String sqlType;//SQL类型，比如select, insert, update, delete
       private String funcName;//方法名
       private String sql;//需要执行的SQL语句
       private Object resultType;//返回类型
       private String parameterType;//参数类型
   }
   
   ```

4. 新建src/main/java/exercise/mainbatis/config/MapperBean.java，作为和Mapper接口映射的MapperBean

   ```java
   package exercise.mainbatis.config;
   
   import lombok.Data;
   
   import java.util.List;
   
   @Data
   public class MapperBean {
       private String interfaceName;//接口名
       private List<Function> functions;//保存的方法信息
   }
   
   ```

5. 修改exercise/mainbatis/sqlsession/Configuration.java，读取XXMapper.xml文件，并根据配置信息创建MapperBean对象

   ```java
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
   ```

6. 在测试类 MainBatisTest 编写方法，检查读取结果

   ```java
       @Test
       public void readMapper() {
           Configuration configuration = new Configuration();
           MapperBean mapperBean = configuration.readMapper("StationMapper.xml");
           System.out.println(mapperBean);
       }
   ```



## 动态代理生成Mapper对象，调用MyExecutor的方法

1. 新建文件 src/main/java/exercise/mainbatis/sqlsession/MapperProxy.java，实现动态代理的方式和Spring框架类似

   ```java
   public class MapperProxy implements InvocationHandler {
       private SQLSession sqlSession;
       private String mapperFile;
       private Configuration configuration;
   
       public MapperProxy(SQLSession sqlSession, Class clazz, Configuration configuration) {
           this.sqlSession = sqlSession;
           this.mapperFile = clazz.getSimpleName() + ".xml";
           this.configuration = configuration;
       }
   
       @Override
       public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           MapperBean mapperBean = configuration.readMapper(this.mapperFile);
           //判断当前传入的方法，是否在xml文件进行指定
           if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
               return null;
           }
   
           List<Function> functions = mapperBean.getFunctions();
           if (null != functions && 0!= functions.size()) {
               for (Function function : functions) {
                   if (method.getName().equals(function.getFuncName())) {
                       //此处做了简化：对于SELECT语句只查询单条结果
                       //原生框架中还需要进行参数解析、拼接字符串、处理返回类型
                       if ("SELECT".equalsIgnoreCase(function.getSqlType())) {
                           return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
                       }
                   }
               }
           }
           return null;
       }
   }
   
   ```

2. 在 SQLSession类中增加返回动态代理对象的方法

   ```java
       public <T> T getMapper(Class<T> clazz) {
           return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxy(this, clazz, configuration))
       }
   ```

3. 新建工厂类 src/main/java/exercise/mainbatis/sqlsession/SessionFactory.java

   ```java
   package exercise.mainbatis.sqlsession;
   
   public class SessionFactory {
       public static SQLSession getSession() {
           return new SQLSession();
       }
   }
   
   ```

4. 在测试类 MainBatisTest 编写方法，查看是否完成动态代理

   ```java
       @Test
       public void getMapper() {
           SQLSession sqlSession = SessionFactory.getSession();
           StationMapper mapper = sqlSession.getMapper(StationMapper.class);
           System.out.println(mapper.getClass());
           Station stationById = mapper.getStationById(1);
           System.out.println(stationById);
       }
   ```

   
