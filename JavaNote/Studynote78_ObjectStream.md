# 对象流

ObjectInputStream 支持将从输入流中读取的数据反序列化为对象，ObjectOutputStream 支持将对象序列化之后写入到输出流。

* 序列化就是在保存数据时，保存数据的值和数据类型
* 反序列化就是在恢复数据时，恢复数据的值和数据类型
* 需要实现序列化接口：Serializable（标记接口，没有方法），或Externalizable 接口（需重写 readExternal 和 writeExternal 方法）

序列化的特点

* 默认序列化所有属性。不想被序列化的信息，可以使用 transient 修饰符，或者设置为静态属性
* 序列化对象时，要求里面属性的类型也需要实现序列化接口（否则抛出 NotSerializableException 异常）
* 序列化具备可继承性，也就是如果某类已经实现了序列化，则它的所有子类也已经默认实现了序列化
* 读写顺序要一致



## 序列化

以内存为基准，把内存中的对象存储到磁盘文件中去，称为对象序列化，对象字节输出流：ObjectOutputStream

- 不建议在一个文件中序列化多个对象，可以将这些对象存储到一个 "集合" 中，然后将集合对象序列化到一个文件中
- 注意：要求被序列化的集合对象、和元素都要实现 Serializable 接口
- 不加泛型 obj instanceof ArrayList

| 方法名                                      | 说明                                       |
| ------------------------------------------- | ------------------------------------------ |
| public ObjectOutputStream(OutputStream out) | 把低级字节输出流包装成高级的对象字节输出流 |
| public final void writeObject(Object obj)   | 把对象写出去到对象序列化流的文件中去       |

### 序列化版本号

序列化的类中建议添加序列化版本号，提高版本的兼容性：private static final long serialVersionUID = 1L;

serialVersionUID 是控制类的版本兼容的，如果不在类中显式定义该属性，Java 会根据类的细节自动生成该属性的值，所以如果对类进行了修改，再重新编译，生成的新的 .class 文件中的 serialVersionUID 很可能会发生变化

反序列化时，会检查序列化文件中和当前本地类的 serialVersionUID 是否一致，若一致则可视为兼容的，不会抛出异常；否则认为是不兼容的，抛出异常，序列化文件中老的 serialVersionUID 对应的数据相当于被抛弃，无法再恢复

序列化后的类，在输出流中会输出所在的包位置。不同包的同名类就算版本号一样，在对编译的Object类向下转型时，也会抛出 ClassCastException 异常

### 代码演示

序列化类

```java
//如果不实现接口，将抛出 NotSerializableException 异常
class ObjectStreamDog implements Serializable {
    private String name;
    private int age;
    //序列化的版本号 serialVersionUID，可以提高兼容性，避免后续读取时报错
    private static final long serialVersionUID = 1L;

    public ObjectStreamDog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
}
```



序列化输出

```java
class ObjectOutStream_ {
    public static void main(String[] args) throws Exception {
        //序列化后，保存的数据内容格式，不是纯文本格式，所以最好按照数据本身的格式来保存
        String filePath = "e:\\data.dat";

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));

        //序列化数据到 e:\data.dat
        oos.writeInt(100);// int -> Integer (实现了 Serializable)
        oos.writeBoolean(true);// boolean -> Boolean (实现了 Serializable)
        oos.writeChar('a');// char -> Character (实现了 Serializable)
        oos.writeDouble(9.5);// double -> Double (实现了 Serializable)
        oos.writeUTF("这里用的方法不是writeString");//String

        //保存一个dog对象
        oos.writeObject(new ObjectStreamDog("旺财", 10));
        oos.close();
        System.out.println("数据保存完毕(序列化形式)");
    }
}
```



## 反序列化

以内存为基准，把存储到磁盘文件中去的对象数据恢复成内存中的对象，称为对象反序列化，对象字节输入流：ObjectInputStream

- 反序列化时，项目中必须要有 "被序列化" 的 "包 + 类"
- 反序列化时，类中的结构可以和文件中之前序列化的结构不同

| 方法名                                   | 说明                                                 |
| ---------------------------------------- | ---------------------------------------------------- |
| public ObjectInputStream(InputStream in) | 把低级字节输如流包装成高级的对象字节输入流           |
| public Object readObject()               | 把存储到磁盘文件中去的对象数据恢复成内存中的对象返回 |

```tex
readObject 的工作流程
1、读取文件                 object.txt
2、获取文件中的全类名        "com.exercise.IO"
3、到当前项目中, "全类名" 所标识的 "包" 下去找相应的类
4、加载这个类
5、将本地 "类中的序列号" 和 "文件中的序列号" 做一次匹配
	     如果匹配成功                                          继续
	     如果匹配失败且没有显式的声明 serialVersionUID           抛出异常
6、创建对象(不调用任何构造方法)
7、将 "堆中属性名" 和 "文件中的属性名" 进行匹配
	     如果匹配上, 就将文件中的值赋给堆中属性的值
```

### 代码演示

```java
//输入文件：反序列化
class ObjectInputStream_ {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //指定反序列化的文件
        String filePath = "e:\\data.dat";

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));

        //读取
        //老师解读
        //1. 读取(反序列化)的顺序需要和你保存数据(序列化)的顺序一致，否则 OptionalDataException 异常
        //2. 担心顺序的，实际项目的时候，一个文件保存一种类型的就行了
        //3. 不能同时存在两个ois.readObject()，否则 EOFException 异常

        System.out.println(ois.readInt());
        System.out.println(ois.readBoolean());

        System.out.println(ois.readChar());
        System.out.println(ois.readDouble());
        System.out.println(ois.readUTF());


        //dog 的编译类型是 Object , dog 的运行类型是 Dog
        Object dog = ois.readObject();
        System.out.println("运行类型=" + dog.getClass());
        System.out.println("dog信息=" + dog);//没有定义toString方法，调用父类Object的方法
        //重写toString方法后可以从返回值看出，static和transient 修饰的属性都是null

        //这里是特别重要的细节:

        //1. 如果我们希望调用Dog的方法, 需要向下转型
        //2. 需要我们将Dog类的定义，放在到可以引用的位置（让ois拥有访问权限）
        ObjectStreamDog dog2 = (ObjectStreamDog)dog;
        System.out.println(dog2.getName()); //旺财..

        //关闭流, 关闭外层流即可，底层会关闭 FileInputStream 流
        ois.close();
    }
}
```

