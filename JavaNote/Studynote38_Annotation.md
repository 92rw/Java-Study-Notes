# 注解
1）注解（Annotation）也被称为元数据（Metadata），用于修饰解释包类、方法、属性、构造器、局部变量等数据信息。
2）和注释一样，注解不影响程序逻辑，但注解可以被编译或运行，相当于嵌入在代码中的补充信息。
3）在JavaSE中，注解的使用目的比较简单，例如标记过时的功能，忽略警告等。在JavaEE中注解占据了更重要的角色，
例如用来配置应用程序的任何切面，代替JavaEE旧版中所遗留的繁冗代码和XML配置等

## 基本的 Annotation介绍
使用 Annotation 时要在其前面增加 @ 符号，并把该 Annotation 当成一个修饰符使用。用于修饰它支持的程序元素
 三个基本的 Annotation:
1）@Override：限定某个方法，是重写父类方法，该注解只能用于方法
2）@Deprecated：用于表示某个程序元素（类，方法等）已过时
3）@SuppressWarnings:抑制编译器警告

补充说明：
@interface不是interface，是在jdk5.0之后加入的注解类
@Target是修饰注解的注解，称为元注解

```java
@Deprecated
class Superclass{
    @Deprecated
    public void fly(){
        System.out.println("Superclass fly");
    }
}

@SuppressWarnings({"unused"})
class Subclass extends Superclass{
    //注解
    //1. @Override 注解在fly方法上，表示子类重写
    //2. 主要是用来编译检查的，不影响程序运行
    //3.写了注解后，编译器会检查是否真得重写，构成重写则编译通过
    // 如果没有构成重写则提示编译错误
    @Override
    public void fly() {
        System.out.println("Subclass fly");
    }
}
```

### @Override

使用说明
1.@Override表示指定重写父类的方法（从编译层面验证），如果父类没有fly方法，则会报错
2.如果不写 @Override 注解，而父类仍有public void fly()，仍然构成重写
3.@Override只能修饰方法，不能修饰其它类，包，属性等等
4.查看@Override注解源码为 @Target(ElementType.METHOD) 说明只能修饰方法

### @Deprecated

1.用于表示某个程序元素（类，方法等）已过时
2.可以修饰方法，类，字段，包，参数等等

3. @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
                     构造器   字段(属性)    局部变量      方法      包       参数     类型
                 4.@Deprecated的作用可以做到新旧版本的兼容和过渡。表示不再推荐使用，但仍然可以使用

### @SuppressWarnings

1.当我们不希望看到警告的时候，可以使用@Suppresswarnings注解来抑制警告信息
2.在{""}中，可以写入你希望抑制（不显示）的警告信息，生成时直接点击左侧的黄色提示就可以选择
3.可以指定的警告类型有：

| 抑制内容          | 说明                                              | 抑制内容                 | 说明                                                   |
| ----------------- | ------------------------------------------------- | ------------------------ | ------------------------------------------------------ |
| all               | 抑制所有警告                                      | rawtypes                 | 忽略没有指定泛型的警告（传参时没有指定泛型）           |
| boxing            | 抑制与封装/拆装作业相关的警告                     | resource                 | 抑制与使用Closeable类型的资源相关的警告                |
| cast              | 抑制与强制转型作业相关的警告                      | restriction              | 抑制与使用不建议或禁止参照相关的警告                   |
| dep-ann           | 抑制与淘汰注释相关的警告                          | serial                   | 抑制与可序列化的类别遗漏serialVersionUID栏位相关的警告 |
| deprecation       | 抑制与淘汰的相关警告                              | static-access            | 抑制与静态存取不正确相关的警告                         |
| fallthrough       | 抑制与switch陈述式中遗漏break相关的警告           | static-method            | 抑制与可能宣告为static的方法相关的警告                 |
| finally           | 抑制与未传回finally区块相关的警告                 | super                    | 抑制与置换方法相关但不含super呼叫的警告                |
| hiding            | 抑制与隐藏变数的区域变数相关的警告                | synthetic-access         | 抑制与内部类别的存取未最佳化相关的警告                 |
| incomplete-switch | 抑制与switch陈述式(enum case)中遗漏项目相关的警告 | sync-override            | 抑制因为置换同步方法而遗漏同步化的警告                 |
| javadoc           | 抑制与javadoc相关的警告                           | unchecked                | 忽略没有检查的警告                                     |
| nls               | 抑制与非nls字串文字相关的警告                     | unqualified-field-access | 抑制与栏位存取不合格相关的警告                         |
| null              | 抑制与空值分析相关的警告                          | unused                   | 忽略没有使用某个变量的警告                             |

4.关于SuppressWarnings 作用范围，和放置的位置相关
    比如 @SuppressWarnings放置在 main方法，那么抑制警告的范围就是 main，通常我们可以放置具体的语句, 方法, 类.

5.看看 @SuppressWarnings 源码
 (1) 放置的位置就是 TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE
 (2) 该注解类有数组 String[] values() 设置一个数组，比如 {"rawtypes", "unchecked", "unused"}



## 元注解

JDK的元Annotation用于修饰其他Annotation，就是注解的注解

- @Target：约束自定义注解只能在哪些地方使用，也就是说给谁添加注解
- @Retention：声明注解的生命周期三种（SOURCE，CLASS，RUNTIME）
- @Documented：是否在文档注释中体现
- @Inherited：是否可以被继承到所标记的类的子类
- @Repeatable：是否可以重复使用

### @Retention注解

用于指定该 Annotation 可以保留多长时间，使用 @Rentention 时必须为该 value 成员变量指定值，@Retention 中可使用的值定义在 RetentionPolicy 枚举类中，常用值如下

- RetentionPolicy.SOURCE：注解只作用在源码阶段，生成的字节码文件中不存在（编译器使用后，直接丢弃这种策略的注释）
- RetentionPolicy.CLASS：注解记录在class文件中，不会被加载到 JVM 中，是默认值
- RetentionPolicy.RUNTIME：注解记录在class文件中（开发常用）程序可以通过反射获取该注释

| @Retention | 源码阶段 | 字节码文件阶段 | 运行阶段 |
| ---------- | -------- | -------------- | -------- |
| SOURCE     | ✓        | ×              | ×        |
| CLASS      | ✓        | ✓              | ×        |
| RUNTIME    | ✓        | ✓              | ✓        |

举例：@Override 的源码为
@Retention(RetentionPolicy.SOURCE)
说明：Override的作用域在SOURCE，当编译器编译时生效，不会写入到.class文件，也不会在runtime（运行时）生效

### @Target

用于修饰Annotation定义，用于指定被修饰的Annotation能用于修饰哪些程序元素.@Target也包含一个名为value的成员变量。@Target 中可使用的值定义在 ElementType 枚举类中，常用值如下

- TYPE：类、接口、枚举
- FIELD：成员变量（属性）
- METHOD：成员方法
- PARAMETER：方法参数
- CONSTRUCTOR：构造器
- LOCAL_VARIABLE：局部变量
- PACKAGE：包
- ANNOTATION_TYPE：注解
- TYPE_PARAMETER：能在类型变量的声明语句中（泛型）
- TYPE_USE：能在使用类型的任何语句中

### @Documented

使用 Javadoc 工具可以从程序源代码中抽取类、方法、成员等注释形成一个和源代码配套的 API 帮助文档，而该工具抽取时默认不包括注解内容

- 被它修饰的注解将被 Javadoc 工具提取成文档
- 定义为 @Documented 的注解必须设置 @Retention(RetentionPolicy.RUNTIME)

### @Inherited注解

被它修饰的Annotation将具有继承性，如果某个类使用了被 @Inherited 修饰的 Annotation ，则其子类将自动具有该注解
说明：实际应用中，使用较少，了解即可。
如果一个超类被该注解标记过的注解进行注解时，如果子类没有被任何注解应用时，该子类就继承超类的注解

### @Repeatable

@Repeatable 是否可以重复使用，在一个地方使用多次相同的注解

```java
// 注解类
@Repeatable(value = ManTypes.class)
public @interface ManType {
    String value() default "";
}
// 注解容器类
public @interface ManTypes {
    ManType[] value();
}
// @ManTypes({@ManType("歌手"), @ManType("超人")})
@ManType(value = "歌手")
@ManType(value = "超人")
public class Test {
}
```



## 自定义注解

特殊属性：value 属性

- 如果只有一个 value 属性的情况下，使用 value 属性的时候可以省略 value 名称不写
- 但是如果有多个属性，且多个属性没有默认值，那么 value 名称是不能省略的

属性的数据类型

- 八种基本数据类型：byte、short、int、long、float、double、boolean、char
- String、Class、注解类型、枚举类
- 以上类型的一维数组形式

```java
public @interface 注解名称 {
    public 属性类型 属性名() default 默认值; // 默认值可以不写
}

// 注解使用
public @interface MyAnnotation {
    public String name();
}
@MyAnnotation(name = "张三")

// 注解使用, key 的名称是 value, key 可以省略不写
public @interface MyAnnotation {
    public String value();
}
@MyAnnotation("张三")

```

## 注解操作

### 注解解析

注解的操作中经常需要进行解析，注解的解析就是判断是否存在注解，存在注解就解析出内容
解析注解的技巧：注解在哪个成分上，我们就先拿哪个成分对象

- 作用在类上，则要该类的 Class 对象，再来拿上面的注解
- 作用方法，则要获得该方法对应的 Method 对象，再来拿上面的注解
- 作用在变量上，则要获得该成员变量对应的 Field 对象，再来拿上面的注解

与注解解析相关的接口

- Annotation：注解的顶级接口，注解都是 Annotation 类型的对象
- AnnotatedElement：该接口定义了与注解解析相关的解析方法

所有的类成分 Class、Method、Field、Constructor，都实现了 AnnotatedElement 接口，它们都拥有解析注解的能力

| 方法                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| boolean isAnnotationPresent(Class<Annotation> annotationClass) | 判断当前对象是否使用了指定的注解，如果使用了则返回 true，否则 false |
| T getDeclaredAnnotation(Class<T> annotationClass)            | 根据注解类型获得对应注解对象                                 |
| Annotation[] getDeclaredAnnotations()                        | 获得当前对象上使用的所有注解，返回注解数组                   |

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Book {
    String value();
    double price() default 100;
    String[] author();
}

@Book(value = "《情深深雨濛濛》", price = 99.9, author = {"琼瑶", "dlei"})
public class BookStore {
    @Book(value = "《三少爷的剑》", price = 399.9, author = {"古龙", "熊耀华"})
    public void test() {}
}

public class Test {
    @Test
    public void parseClass() {
        Class c = BookStore.class; // 先得到类对象
        // 判断这个类上面是否存在这个注解
        if(c.isAnnotationPresent(Book.class)) {
            Book book = (Book) c.getDeclaredAnnotation(Book.class); // 直接获取该注解对象
            System.out.println(book.value());
            System.out.println(book.price());
            System.out.println(Arrays.toString(book.author()));
        }
    }

    @Test
    public void parseMethod() throws NoSuchMethodException {
        Class c = BookStore.class;              // 先得到类对象
        Method m = c.getDeclaredMethod("test"); // 再得到方法对象
        // 判断这个类上面是否存在这个注解
        if(m.isAnnotationPresent(Book.class)) {
            Book book = (Book) m.getDeclaredAnnotation(Book.class); // 直接获取该注解对象
            System.out.println(book.value());
            System.out.println(book.price());
            System.out.println(Arrays.toString(book.author()));
        }
    }
}


```

### 执行带注解的方法

```java
public class Test {
    public void test1(){
        System.out.println("test1");
    }

    @MyTest
    public void test2(){
        System.out.println("test2");
    }

    @MyTest
    public void test3(){
        System.out.println("test3");
    }

    // 启动菜单: 有注解的才被调用
    public static void main(String[] args) throws Exception {
        Test t = new Test();
        Class c = Test.class; // 获取类对象
        Method[] methods = c.getDeclaredMethods(); // 提取全部方法
        // 遍历方法, 看是否有 MyTest 注解, 有就跑它
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyTest.class)){
                method.invoke(t); // 跑它
            }
        }
    }
}

```

## 预制注解

 Java 语言自身提供的注解

| 注解              | 说明                                                     |
| ----------------- | -------------------------------------------------------- |
| @auther           | 标明开发该类模块的作者，多个作者之间使用 , 分割          |
| @version          | 标明该类模块的版本                                       |
| @see              | 参考转向，也就是相关主题                                 |
| @since            | 从哪个版本开始增加的                                     |
| @param            | 对方法中某参数的说明，如果没有参数就不能写               |
| @return           | 对方法返回值的说明，如果方法的返回值类型是 void 就不能写 |
| @execption        | 对方法可能抛出的异常进行说明                             |
| @Override         | 限定重写父类方法，该注解只能用于方法                     |
| @Deprecated       | 用于表示所修饰的元素（类、方法等）已过时                 |
| @SuppressWarnings | 抑制编译器警告                                           |





参考资料

[4、注解 - lidongdongdong~ - 博客园 (cnblogs.com)](https://www.cnblogs.com/lidong422339/p/17472249.html#4有注解就执行)
