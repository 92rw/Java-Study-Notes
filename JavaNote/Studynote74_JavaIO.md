# Java IO

I / O 全称为 Input / Output，中文为输入 / 输出

- 在计算机中，常用的 I / O 设备有：硬盘、网络、键盘、显示器等
- 在操作系统层面， I / O 系统有：文件、网络、标准输入和输出（对应键盘和显示器）、管道等
- Java 提供的 I / O 类库就是用来读写这些 I / O 系统的，Java I / O 类库主要有两个：java.io 类库和 java.nio 类库

流最大的特点就是不复用，流中的数据使用完成之后就不能再使用

## File 类

定位文件，获取文件本身的信息、删除文件、创建文件（文件夹）等功能

创建 File 对象

| 方法名                                   | 说明                                               |
| ---------------------------------------- | -------------------------------------------------- |
| public File(String pathname)             | 根据文件路径创建文件对象                           |
| public File(String parent, String child) | 从父路径名字符串和子路径名字符串创建文件对象       |
| public File(File parent, String child)   | 根据父路径对应文件对象和子路径名字符串创建文件对象 |



```java
File file = new File("文件 / 文件夹 / 绝对路径 / 相对路径");
File file = new File("D:\\itheima\\a.txt"); // 绝对路径
File file = new File("a.txt");              // 相对路径

```

常用方法

| 方法名                          | 说明                           | 方法名                          | 说明                                                      |
| ------------------------------- | ------------------------------ | ------------------------------- | --------------------------------------------------------- |
| public boolean isDirectory()    | 判断 File 是否为文件夹         | public  boolean createNewFile() | 创建一个新的空的文件                                      |
| public boolean isFile()         | 判断 File 是否为文件           | public  boolean mkdir()         | 只能创建一级文件夹                                        |
| public boolean exists()         | 判断 File 是否存在             | public  boolean mkdirs()        | 可以创建多级文件夹                                        |
| public String getAbsolutePath() | 获取绝对路径                   | public  boolean delete()        | 删除由此 File 所表示的文件或空文件夹，不走 windows 回收站 |
| public String getPath()         | 获取 File 定义时使用的路径     | public  String[] list()         | 获取当前目录下所有的 "一级文件名称"                       |
| public String getName()         | 获取文件或文件夹的名称         | public  File[] listFiles()      | 获取当前目录下所有的 "一级文件对象"（重点）               |
| public String getParent()       | 获取文件或文件夹的父绝对名称   | public  long lastModified()     | 获取文件最后修改的时间毫秒值                              |
| boolean renameTo(File dest)     | 对文件重命名（不同路径会剪切） | public  long length()           | 获取文件的大小，不能获取目录的大小，单位：B               |

备注：

* mac系统不知道路径的可以String home = System.getProperty("user.home"); 然后再用 String filePath = home + "/Desktop/xxx/xxx.txt";
* delete() ：即使文件被占用，也会删除，不走回收站，方法默认只能删除文件和空文件夹

listFiles() 注意事项
调用者需要：判断数组是否为空、判断数组长度是否为 0、判断数组元素是否为文件夹

- 当调用者是一个文件时，返回 null
- 当调用者不存在时（目录不存在），返回 null
- 当调用者是一个需要权限才能进入的文件夹时，返回 null
- 当调用者是一个空文件夹时，返回一个长度为 0 的数组

正常情况

- 当调用者是一个有内容的文件夹时，将里面所有文件和文件夹的路径放在 File 数组中返回
- 当调用者是一个有隐藏文件的文件夹时，将里面所有文件和文件夹的路径放在 File 数组中返回，包含隐藏内容

```java
//文件搜索
public static void searchFile(File dir, String filename) {
    if (dir.isDirectory()) {
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().equals(filename)) {
                        System.out.println(file.getAbsoluteFile());
                        // 启动软件(.exe)
                        // Runtime r = Runtime.getRuntime();
                        // r.exec(file.getAbsolutePath());
                    }
                } else {
                    searchFile(file, filename);
                }
            }
        }
    } else {
        System.out.println("参数不是文件夹");
    }
}

public static void main(String[] args) {
    File file = new File("D:\\BaiduNetdiskDownload");
    searchFile(file, "day24、XML、解析、设计模式等.rar");
}

```



## 字符集

计算机底层可以表示十进制编号，计算机可以给人类字符进行编号存储，这套编号规则就是字符集

- ASCII 使用 1 个字节存储一个字符，一个字节是 8 位，总共可以表示 128 个字符信息，对于英文，数字来说是够用的
- GBK 是 windows 系统默认的码表，是中国的码表，一个中文以 2 个字节的形式存储，包含了几万个汉字，兼容 ASCII 码表
- Unicode 是万国码，以 UTF-8 编码后一个中文一般以 3 个字节的形式存储，UTF-8 也要兼容 ASCII 编码表
- 技术人员都应该使用 UTF-8 的字符集编码，编码前和编码后的字符集需要一致，否则会出现中文乱码

字符串常见的字符底层组成

- 英文和数字等在任何国家的字符集中都占 1 个字节
- GBK 字符中一个中文字符占 2 个字节
- UTF-8 编码中一个中文一般占 3 个字节

编码前的字符集和编码好的字符集有什么要求？

- 必须一致，否则会出现中文字符乱码
- 英文和数字在任何国家的编码中都不会乱码

| String编码                          | 字符串到字节                                                 | String解码                                                   | 字节到字符串                                                |
| ----------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ----------------------------------------------------------- |
| byte[] getBytes()                   | 使用平台的默认字符集将该 String 编码为一系列字节，将结果存储到新的字节数组中 | String(byte[]  bytes)                                        | 通过使用平台的默认字符集解码指定的字节数组来构造新的 String |
| byte[] getBytes(String charsetName) | 使用指定的字符集将该 String 编码为一系列字节，将结果存储到新的字节数组中 | String(byte[]  bytes, String charsetName)                    | 通过指定的字符集解码指定的字节数组来构造新的 String         |
| public char[] toCharArray()         | 将当前字符串转换成字符数组返回                               | String(byte[]  bytes, int offset, int length)                | 读多少数据，倒多少数据                                      |
|                                     |                                                              | String(byte[]  bytes, int offset, int length, String charsetName) | 读多少数据，倒多少数据，指定编码                            |

乱码出现原因

| 名称   | 示例                       | 特点                                                   | 原因                                    |
| ------ | -------------------------- | ------------------------------------------------------ | --------------------------------------- |
| 口字码 | СС��                       | 大部分字符是问号小方块                                 | UTF-8解码GBK编码的中文                  |
| 锟拷体 | 锟斤拷小小锟斤拷学习锟斤拷 | 全中文字符，大部分都是"锟斤拷"这几个字符               | UTF-8读取GBK编码的中文，用GBK再次读取   |
| 古文码 | 灏忓皬鏄庢湀               | 大部分都是生僻字，像古文                               | GBK解码UTF-8编码的中文汉字              |
| 问句码 | 小小?                      | 字符串长度为奇数时，结尾为问号；字符串长度为偶数时正确 | GBK读取UTF-8编码的中文，用UTF-8再次读取 |
| 符号码 | å¥½å¥½å\xad¦å¤©å¤©å\xad¦   | 大部分字符为各种符号                                   | ISO8859-1编码读取UTF-8编码的中文汉字    |
| 拼音码 | ºÃºÃÑ§Ï°ÌìÌìÏòÉÏ           | 大部分字符都是带有声调的字母                           | ISO8859-1编码读取GBK编码的中文汉字      |



## JavaIO体系

1. I/O是Input/Output的缩写，I/O技术是非常实用的技术，用于处理数据传输，如读/写文件，网络通讯等，IO 默认不可以拷贝文件夹
2. Java程序中，对于数据的输入/输出操作以流（stream）”的方式进行
3. java.io包下提供了各种“流”类和接口，用以获取不同种类的数据，并通过方法输入或输出数据
4. 输入input：读取外部数据（磁盘、光盘等存储设备的数据）到程序（内存）中，输出output 将程序 (内存）数据输出到磁盘、光盘等存储设备中

IO流常用于以后的导入到处文件图片文本word，Excel等等，综合的知识点非常多，抽象继承多态等等

### 流的分类

按流的角色的不同分为：节点流，处理流/包装流
按数据流的流向不同分为：输入流，输出流

* 输入流：将文件、网络、标准输入（System.in）、管道中的数据，输入到内存中
* 输出流：将内存中的数据输出到文件、网络、标准输出（System.out、System.err）、管道中
* 简记：输入流还是输出流，看内存上的操作，read方法还是write方法看磁盘上的操作

按操作数据单位不同分为：字节流（8bit）->二进制文件，字符流（按字符）->文本文件

- 字节流适合做一切文件数据的拷贝（音视频，文本）
- 字节流不适合读取中文内容输出
- 字符流适合做文本操作（读，写）

关于乱码

- 字节流读取后，byte[] 转 string 的时候可以指定编码
- 字符流读取的时候，就需要指定编码，也就是在构造器部分就需要指定编码，char[] 转 string 不能指定编码
- 缓冲流是对子节流和字符流的进一步封装，不能指定编码
- 转换流可以指定编码

| 分类       | 字节输入流           | 字节输出流            | 字符输入流        | 字符输出流         |
| ---------- | -------------------- | --------------------- | ----------------- | ------------------ |
| 抽象基类   | InputStream          | OutputStream          | Reader            | Writer             |
| 访问文件   | FileInputStream      | FileOutputStream      | FileReader        | FileWriter         |
| 访问数组   | ByteArrayInputStream | ByteArrayOutputStream | CharArrayReader   | CharArrayWriter    |
| 访问管道   | PipedInputStream     | PipedOutputStream     | PipedReader       | PipedWriter        |
| 访问字符串 |                      |                       | StringReader      | StringWriter       |
| 缓冲流     | BufferdInputStream   | BufferedOutputStream  | BufferedReader    | Bufferedwriter     |
| 转换流     |                      |                       | InputStreamReader | OutputStreamWriter |
| 对象流     | ObjectInputStream    | ObjectOutputStream    |                   |                    |
|            | FilterInputStream    | FilterOutputStream    | FilterReader      | FilterWriter       |
| 打印流     |                      | PrintStream           |                   | PrintWriter        |
| 推回输入流 | PushbackInputStream  |                       | PushbackReader    |                    |
| 数据流     | DataInputStream      | DataOutputstream      |                   |                    |

* Java的IO流共涉及40多个类，实际上非常规则，都是从4个抽象基类派生的
* 由这四个类派生出来的子类名称都是以其父类名作为子类名后缀



## RandomAccessFile

java.io.RandomAccessFile 类主要支持对随机访问文件的读写操作

- r：以只读方式打开
- rw：打开以便读取和写入
- rwd：打开以便读取和写入，同步文件内容的更新
- rws：打开以便读取和写入，同步文件内容和元数据的更新

| 方法名                                     | 说明                                           |
| ------------------------------------------ | ---------------------------------------------- |
| RandomAccessFile(String name, String mode) | 根据参数指定的名称和模式构造对象               |
| int read()                                 | 读取单个字节的数据                             |
| void seek(long pos)                        | 用于设置从此文件的开头开始测量的文件指针偏移量 |
| void write(int b)                          | 将参数指定的单个字节写入                       |
| void close()                               | 用于关闭流并释放有关的资源                     |

## commons-io

commons-io 是 apache 开源基金组织提供的一组有关 IO 操作的类库，可以提高 IO 功能开发的效率
commons-io 工具包提供了很多有关 IO 操作的类，有两个主要的类 FileUtils、IOUtils
将 commons-io-2.11.0.jar 文件复制到 lib 文件夹就可以使用了

| FileUtils类方法                                          | 说明                         |
| -------------------------------------------------------- | ---------------------------- |
| String readFileToString(File file, String encoding)      | 读取文件中的数据，返回字符串 |
| void copyFile(File srcFile, File destFile)               | 复制文件                     |
| void copyDirectoryToDirectory(File srcDir, File destDir) | 复制文件夹                   |



## 序列化和反序列化

需要实现序列化接口：implements Serializable
序列化版本号：private static final long serialVersionUID = 1L;
不想被序列化的信息：transient，转瞬即逝的，而且静态属性不会被序列化

### 序列化

以内存为基准，把内存中的对象存储到磁盘文件中去，称为对象序列化，对象字节输出流：ObjectOutputStream

- 不建议在一个文件中序列化多个对象，可以将这些对象存储到一个 "集合" 中，然后将集合对象序列化到一个文件中
- 注意：要求被序列化的集合对象、和元素都要实现 Serializable 接口
- 不加泛型 obj instanceof ArrayList

| 方法名                                      | 说明                                       |
| ------------------------------------------- | ------------------------------------------ |
| public ObjectOutputStream(OutputStream out) | 把低级字节输出流包装成高级的对象字节输出流 |
| public final void writeObject(Object obj)   | 把对象写出去到对象序列化流的文件中去       |

### 反序列化

以内存为基准，把存储到磁盘文件中去的对象数据恢复成内存中的对象，称为对象反序列化，对象字节输入流：ObjectInputStream

- 反序列化时，项目中必须要有 "被序列化" 的 "包 + 类"
- 反序列化时，类中的结构可以和文件中之前序列化的结构不同

| 方法名                                   | 说明                                                 |
| ---------------------------------------- | ---------------------------------------------------- |
| public ObjectInputStream(InputStream in) | 把低级字节输如流包装成高级的对象字节输入流           |
| public Object readObject()               | 把存储到磁盘文件中去的对象数据恢复成内存中的对象返回 |

```markdown
readObject 的工作流程
1、读取文件                 object.txt
2、获取文件中的全类名        "com.zzw.student"
3、到当前项目中, "全类名" 所标识的 "包" 下去找相应的类
4、加载这个类
5、将本地 "类中的序列号" 和 "文件中的序列号" 做一次匹配
	     如果匹配成功                                          继续
	     如果匹配失败且没有显式的声明 serialVersionUID           抛出异常
6、创建对象(不调用任何构造方法)
7、将 "堆中属性名" 和 "文件中的属性名" 进行匹配
	     如果匹配上, 就将文件中的值赋给堆中属性的值

```

参考资料

[1、Java IO - lidongdongdong~ - 博客园 (cnblogs.com)](https://www.cnblogs.com/lidong422339/p/17467298.html#3io-体系)
