# 反射

反射是指对于任何一个 Class 类，在 "运行的时候" 都可以直接得到这个类全部成分

- 在运行时，可以直接得到这个类的构造器对象：Constructor
- 在运行时，可以直接得到这个类的成员变量对象：Field
- 在运行时，可以直接得到这个类的成员方法对象：Method
- 这种运行时动态获取类信息以及动态调用类中成分的能力称为 Java 语言的反射机制

```markdown
* Declared:   声明的
* 构造器:      Constructors
* 属性:        Fields
* 修饰符:      Modifiers
* 返回类型:    ReturnType
* 方法名:      Name
* 参数列表:    ParameterTypes
* 注解:       Annotations
* 异常:       ExceptionTypes
* 调用:       invoke
* 接口:       Interfaces
* 父类:       Superclass
* 包:         Package
```

反射是为了解决在运行期，对某个实例一无所知的情况下，如何调用其方法。

反射的关键：先得到编译后的 Class 类对象，然后就可以得到 Class 的全部成分

反射可以破坏封装性，私有的属性和方法也可以使用了

需求：通过外部文件配置，在不修改源码的情况下，执行程序的相关代码。遵循设计模式的ocp原则（开闭原则：不修改源码，扩容功能）

```java
//1. java.lang.Class：代表一个类，Class 对象表示某个类加载后在堆中的对象
Class cls = Class.forName("类的位置及名称"); //取得类的定义，但没有实例化
Object o = cls.newInstance();	//传入一个执行这个方法，取得真正实例化的对象

//2. java.lang.reflect.Method：代表类的方法，Method 对象表示某个类的方法
Method method = cls.getMethod("方法名");
method.invoke(o); //传统方法 对象.方法() , 反射机制 方法.invoke(对象)

//3. java.lang.reflect.Field：代表类的成员变量，Field 对象表示某个类的成员变量
Field field = cls.getField("成员变量名");
System.out.println(field.get(o)); // 传统写法 对象.成员变量 , 反射: 成员变量对象.get(对象)

//4.java.lang.reflect.Constructor：代表类的构造方法
Constructor constructor1 = cls.getConstructor(); 
System.out.println(constructor1); //()中可以指定构造器参数类型, 返回无参构造器
Constructor constructor2 = cls.getConstructor(String.class); 
System.out.println(constructor2); //此处调用的是传入String参数的构造器
```

注意：

1. class.forName( )方法中一定要填写完整类名（即包名+类名），即便该类与调用forName方法的类在同一包下，也需填写完整类名！

2. newInstance() 方法在后续版本中提示过时，在jdk9开始使用 .getDeclaredConstructor().newInstance() 替代（多了一步获取构造器）



## java.lang.Class类

相关方法

| 方法                                  | 描述                                             |
| ------------------------------------- | ------------------------------------------------ |
| Package getPackage()                  | 获取当前类所在的包                               |
| String getSimpleName()                | 获取当前类的类名                                 |
| String getName()                      | 获取当前类的全路径名（包名 ＋ 类名）             |
| Class[] getInterfaces()               | 获取当前类实现的接口（不包括父类）               |
| Annotation[] getAnnotations()         | 获取修饰当前类的注解                             |
| Class getSuperclass()                 | 获取当前类的父类的字节码信息                     |
| Object newInstance()                  | 调用类中的无参构造器(public)，获取对应类一个实例 |
| Class forname(String name)            | 返回指定类名的Class对象                          |
| ClassLoader getClassLoader()          | 返回该类的类加载器                               |
| boolean isAssignableFrom(Class clazz) | 判断clazz类是否是当前类的子类                    |

说明：

1. Class也是类，因此也继承Obiect类，java.lang.Class
2. Class类对象不是new出来的，而是系统创建的
3. 对于某个类的Class类对象，在内存中只有一份，因为类只加载一次
4. 每个类的实例都会记得自己是由哪个Class实例所生成
5. 通过Class对象可以完整地得到一个类的完整结构,通过一系列API
6. Class对象是存放在堆的
7. 类的字节码二进制数据，是放在方法区的，有的地方称为类的元数据(包括方法代码、变量名、方法名、访问权限等等）
8. 当我们判断一个实例是否是某个类型时，用`instanceof`不但匹配指定类型，还匹配指定类型的子类。而用`==`判断`class`实例可以精确地判断数据类型，但不能作子类型比较
9. 通过`Class`对象的`isAssignableFrom()`方法可以判断一个向上转型是否可以实现。

```java
/*
如下类型有Class对象
1．外部类，成员内部类，青静态内部类，局部内部类，匿名内部类
2.interface：接口
3.数组
4.enum：枚举 -> 枚举就是一个类里面一堆静态常量的简写形式
5.annotation：注解 -> 注解类也可以自己定义
6.基本数据类型
7.void -> 没有返回值也属于一种返回值，就是void对象的功能
 */
class ClassInstances {
    public static void main(String[] args) {
        Class<String> cls1 = String.class;//外部类
        Class<Serializable> cls2 = Serializable.class;//接口
        Class<Integer[]> cls3 = Integer[].class;//数组
        Class<float[][]> cls4 = float[][].class;//二维数组
        Class<Deprecated> cls5 = Deprecated.class;//注解
        Class<Thread.State> cls6 = Thread.State.class;//枚举
        Class<Long> cls7 = long.class;//基本数据类型
        Class<Void> cls8 = void.class;//void数据类型 -> void是一个返回类型，代表是个类
        Class<Class> cls9 = Class.class;//Class类本身也是一种类

        System.out.println(cls1);
        System.out.println(cls2);
        System.out.println(cls3);
        System.out.println(cls4);
        System.out.println(cls5);
        System.out.println(cls6);
        System.out.println(cls7);
        System.out.println(cls8);
        System.out.println(cls9);
    }
}
```



## 其他类和方法

### 常用共有方法

| 方法                                    | 说明                                                         |
| --------------------------------------- | ------------------------------------------------------------ |
| int getModifiers()                      | 获取当前属性的访问修饰符                                     |
| String getName()                        | 获取当前属性的名称                                           |
| public void setAccessible(boolean flag) | 设置为 true，表示取消访问检查（仅这一次），进行暴力反射（暴破） |

反射调用优化-关闭访问检查

1. Method 和 Field、Constructor 对象都有 setAccessible() 方法
2. setAccessible 作用是启动和禁用访问安全检查的开关
3. 参数值为true表示反射的对象在使用时取消访问检查，提高反射的效率。参数值为false则表示反射的对象执行访问检查。
4. 如果JVM运行期存在`SecurityManager`，那么它会根据规则进行检查，有可能阻止`setAccessible(true)`。例如，某个`SecurityManager`可能不允许对`java`和`javax`开头的`package`的类调用`setAccessible(true)`，这样可以保证JVM核心库的安全。

getModifiers 以int形式返回修饰符，相当于2进制每一位表示一种修饰，多种修饰符则用两个相加

* 默认修饰符 是0 ， public  是1 ，private 是 2 ，protected 是 4 , static 是 8 ，final 是 16



### java.lang.reflect.Constructor类：构造器对象

| 方法                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Constructor<?>[] getConstructors()                           | 返回本类所有构造器对象的数组（只能拿 public 的）             |
| Constructor<?>[] getDeclaredConstructors()                   | 返回本类所有构造器对象的数组，存在就能拿到                   |
| Constructor&lt;T> getConstructor(Class<?>... parameterTypes) | 返回单个构造器对象（只能拿 public 的）                       |
| Constructor&lt;T> getDeclaredConstructor(Class<?>... parameterTypes) | 返回单个构造器对象，存在就能拿到                             |
| public T newInstance(Object... initargs)                     | 根据指定的构造器创建对象，参数为 "构造器参数"，抛出 InstantiationException 异常 |
| getParameterTypes                                            | 以 Class[] 返回参数类型数组                                  |

`Constructor`总是当前类定义的构造方法，和父类无关，因此不存在多态的问题。不能通过子类Class对象拿到父类的构造器，因为这代表着你能用子类Class对象直接实例化父类对象，而正确的方式应该是先通过子类获取父类Class对象，再获取其构造器



### java.lang.reflect.Fields类：变量对象

| 方法                                | 说明                                                         |
| ----------------------------------- | ------------------------------------------------------------ |
| Field[] getFields()                 | 返回所有成员变量对象的数组（只能拿 public 的，可以拿父类）   |
| Field[] getDeclaredFields()         | 返回所有成员变量对象的数组，存在就能拿到（包含被保护、无修饰、私有属性，不能拿父类） |
| Field getField(String name)         | 返回单个成员变量对象（只能拿 public 的，可以拿父类）         |
| Field getDeclaredField(String name) | 返回单个成员变量对象，存在就能拿到（不能拿父类）             |
| void set(Object obj, Object value)  | 添加属性，其中第一个`Object`参数是指定的实例，第二个`Object`参数是待修改的值 |
| Object get(Object obj)              | 获取指定实例的指定字段的值                                   |
| Class<?> getType()                  | 获取当前属性的类型                                           |

私有的属性无法通过getField 方法获取，会抛出 NoSuchFieldException 异常

如果获取/设置的是静态属性，则set和get中的参数，可以写成null

通过反射给属性赋值 -> 抛出 IllegalAccessException 异常

* 字段和方法用setAccessible方法，授权给私有和受保护方法，使之可以被访问

### java.lang.reflect.Method类：方法对象

| 方法                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| Method[] getMethods()                                        | 返回所有成员方法对象的数组（只能拿 public 的，可以拿父类）   |
| Method[] getDeclaredMethods()                                | 返回所有成员方法对象的数组，存在就能拿到（ 包含被保护、无修饰、私有方法，不能拿父类） |
| Method getMethod(String name, Class<?>... parameterTypes)    | 返回单个成员方法对象（只能拿 public 的，可以拿父类）         |
| Method getDeclaredMethod(String name, Class<?>... parameterTypes) | 返回单个成员方法对象，存在就能拿到（不能拿父类）             |
| Object invoke(Object obj, Object... args)                    | 运行方法 参数一：用 obj 对象调用该方法 参数二：调用方法的传递的参数（如果没有就不写） 返回值：方法的返回值（如果没有就不写） |
| getReturnType()                                              | 获取当前方法返回类型，Class类型                              |
| getParameterTypes()                                          | 获取当前方法的参数列表，Class[]类型                          |
| getExceptionTypes()                                          | 获取当前方法的异常                                           |

说明：

```java
Object returnValue = m.invoke(o,实参列表); //o就是对象
//如果是静态方法，则invoke的参数o，可以写成null
```

### 其他方法

| 方法名         | 返回类型     | 说明         |
| -------------- | ------------ | ------------ |
| getPackage     | Package      | 返回包信息   |
| getSuperClass  | Class        | 返回父类信息 |
| getInterfaces  | Class[]      | 返回接口信息 |
| getAnnotations | Annotation[] | 返回注解信息 |



## 反射案例

```java
/**练习1
 * 1.定义PrivateTest类，有私有name属性，并且属性值为hellokitty
 * 2.提供getName的公有方法
 * 3.创建PrivateTest的类，利用Class类得到私有的name属性，修改私有的name属性值，并调用getNameO的方法打印修改后的name属性值
 */
class ReflectionPrivateTest {
    private String name = "hellokitty";
    //默认无参构造器
    public String getName() {
        return name;
    }
}
class ReflectionExercise01 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        //1.得到类对应的Class对象(因为在同一个java文件，运行时直接会传入对象实例，可以用这个方法调用)
        Class<ReflectionPrivateTest> ref01 = ReflectionPrivateTest.class;
        //2.得到类对象实例
        ReflectionPrivateTest o = ref01.newInstance();//方法二用泛型，所以编译类型不是Object
        //3。得到该属性的对象
        Field name = ref01.getDeclaredField("name");
        //4.私有属性需要爆破
        name.setAccessible(true);
        name.set(o, "哆啦A梦");
        //5.得到getName方法，不需要参数的方法直接输入方法名即可
        Method getName = ref01.getMethod("getName");
        //6.public方法可以直接调用
        Object invoke = getName.invoke(o);
        System.out.println("name = " + invoke);
    }
}

/**练习2
 * 利用Class类的forName方法得到File类的class 对象
 * 在控制台打印File类的所有构造器
 * 通过newInstance的方法创建File对象，并创建D:\mynew.txt文件
 */

class ReflectionExercise02{
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //1. Class类的forName方法得到File类的class 对象
        Class<?> file = Class.forName("java.io.File");
        //2.得到所有构造器
        Constructor<?>[] constructors = file.getDeclaredConstructors();
        //遍历输出
        for (Constructor<?> constructor : constructors) {
            System.out.println("File类的构造器" + constructor);
        }
        //3.得到实例化对象
        //File类不能直接newInstance，因为File类没有无参构造器
        //如果实例化会出现异常，那么只能先通过getConstructor()调用有参构造器
        Constructor<?> constructor = file.getDeclaredConstructor(String.class);
        String path = "D:\\mynew.txt";
        //创建新对象
        Object o = constructor.newInstance(path);
        System.out.println(o.getClass());
        //得到方法并调用
        Method createNewFile = file.getMethod("createNewFile");
        createNewFile.invoke(o);
        System.out.println(path +" 创建成功");
    }
}
```



参考资料

[秒懂Java之反射 - ShuSheng007](https://shusheng007.top/2021/09/09/java-reflection/)

[反射源码解析](https://www.cnblogs.com/chanshuyi/p/head_first_of_reflection.html#反射源码解析)
