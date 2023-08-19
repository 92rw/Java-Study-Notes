# 单元测试

## 为什么需要Unit

1．一个类有很多功能代码需要测试，为了测试，就需要写入到main方法中
2．如果有多个功能代码测试，就需要来回注销，切换很麻烦
3．如果可以直接运行一个方法，就方便很多，并且可以给出相关信息，就好了->JUnit



## 引入junit进行单元测试

基本介绍
1.JUnit是一个Java语言的单元测试框架
2.多数ava的开发环境都已经集成了Unit作为单元测试的工具

使用方法：在需要测试的代码段上面输入 @Test 键盘Alt+Enter，选择"Add 'JUnit5.4' to classpath
注意事项：进行单元测试的方法不能有返回值、不能私有化

```java
/**
 * 定义一个泛型类 DAO<T>，在其中定义一个Map 成员变量，Map 的键为 String 类型，值为 T 类型。
 *
 * 分别创建以下方法：
 * (1) public void save(String id,T entity)： 保存 T 类型的对象到 Map 成员变量中
 * (2) public T get(String id)：从 map 中获取 id 对应的对象
 * (3) public void update(String id,T entity)：替换 map 中key为id的内容,改为 entity 对象
 * (4) public List<T> list()：返回 map 中存放的所有 T 对象
 * (5) public void delete(String id)：删除指定 id 对象
 *
 * 定义一个 User 类：
 * 该类包含：private成员变量（int类型） id，age；（String 类型）name。
 *
 * 创建 DAO 类的对象， 分别调用其 save、get、update、list、delete 方法来操作 User 对象，
 * 使用 Junit 单元测试类进行测试。
 *
 * 思路分析
 * 1. 定义User类
 * 2. 定义Dao<T>泛型类
 */
 class DAO<T> {//泛型类
    //如果没有实例化Map，会提示空指针异常
    private Map<String, T> map = new HashMap<>();

    public T get(String id) {
        return map.get(id);
    }
    public void update(String id,T entity) {
        map.put(id, entity);
    }
    //返回 map 中存放的所有 T 对象
    //遍历map [k-v],将map的 所有value(T entity),封装到ArrayList返回即可
    public List<T> list() {
        //创建 Arraylist
        List<T> list = new ArrayList<>();

//        //用keySet遍历map，得到value并赋给list集合
//        Set<String> keySet = map.keySet();
//        for (String key : keySet) {
//            //map.get(key) 返回就是 User对象->ArrayList
//            list.add(map.get(key));//也可以直接使用本类的 get(String id)
//        }
        //另一种思路：values方法返回一个Collections，然后用addall全部加入到List集合返回不就好了
        //注意：values返回对象的运行类型并没有继承List接口，不可以向下强转
        Collection<T> values = map.values();
        list.addAll(values);
        return list;
    }
    public void delete(String id) {
        map.remove(id);
    }
    public void save(String id,T entity) {//把entity保存到map
        map.put(id, entity);
    }
}
```

User类

```java
class User {
    private int id;
    private int age;
    private String name;

    public User(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
```

在main方法测试前，先进行单元调试

```java
import org.junit.jupiter.api.Test;

import java.util.*;

public class Studynote66_UnitTest {
    public static void main(String[] args) {

    }
    @Test
    public void TestList(){
        DAO<User> userDAO = new DAO<>();
        DAO<User> dao = new DAO<>();
        dao.save("001", new User(1,10,"jack"));
        dao.save("002", new User(2,18,"king"));
        dao.save("003", new User(3,38,"smith"));

        List<User> list = dao.list();

        System.out.println("list=" + list);

        dao.update("003", new User(3, 58, "milan"));
        dao.delete("001");//删除
        System.out.println("===修改后====");
        list = dao.list();
        System.out.println("list=" + list);

        System.out.println("id=003 " + dao.get("003"));//milan

    }
}
```





## 在内存中测试文件操作

跟内存读写相关的类有：ByteArrayInputStream、ByteArrayOutputStream、CharArrayReader、CharArrayWriter、StringReader、StringWriter
我们将内存看做一种特殊的 I / O 系统，也可以像文件一样，当作 Stream 来读写

在大部分情况下，我们都不需要这些内存读写类，直接对 byte 数组、char 数组进行读写即可，没必要将它们封装成流来操作，这些类的主要作用是实现兼容

比如：我们使用第三方类库中的某个函数，来处理 byte 数组中的数据，但这个函数的输入参数是 InputStream 类型的
为了兼容这个函数的定义，我们就可以将待处理的 byte 数组，封装成 ByteArrayInputStream 对象，再传递给这个函数处理

```java
byte[] source = "需要处理的字符串/文件内容".getBytes();
InputStream in = new ByteArrayInputStream(source);
// 接下来就可以跟处理其他 InputStream 一样处理 source 了

```

在编写单元测试时，这些内存读写类也非常有用，可以替代文件或网络，将测试数据放置于内存，准备起来更加容易
如下代码所示，假设要为 readFromFile() 这个函数编写单元测试代码，我们需要创建文件，写入测试数据，并且放置到合适的地方，做一堆准备工作才能完成测试
如果使用 ByteArrayInputStream，我们便可以在内存中构建测试数据，这样就方便了很多

```java
// 待测试函数
public int readFromFile(InputStream inputStream) {
    // ...
}

// 测试代码
public void test_readFromFile() {
    byte[] testData = new byte[512];
    // ... 构建测试数据, 填入 testData 数组 ...
    InputStream in = new ByteIntputStream(testData);
    int res = readFromFile(in);
    // ... assert ... 判断返回值是否符合预期 ...
}
```
