# IO流的分类

## 文件流

### 文件字节流

#### FileInputStream

文件字节输入流：读取中文字符无法避免乱码问题

| 方法名                                          | 说明                                                         |
| ----------------------------------------------- | ------------------------------------------------------------ |
| public FileInputStream(File file)               | 创建字节输入流管道与源文件对象接通                           |
| public FileInputStream(String pathname)         | 创建字节输入流管道与源文件路径接通                           |
| public int read()                               | 每次读取一个字节返回，如果字节已经没有可读的返回 -1          |
| public int read(byte[] buffer)                  | 每次读取一个字节数组返回，如果字节已经没有可读的返回 -1（返回读取字节数） |
| public byte[] readAllBytes() throws IOException | 直接将当前字节输入流对应的文件对象的字节数据装到一个字节数组返回 |

```java
InputStream is = new FileInputStream("src/ab.txt");

int len;
byte[] buffer = new byte[3];

while ((len = is.read(buffer)) != -1) {
    System.out.println(new String(buffer, 0, len)); // 读多少数据, 倒多少数据
}

byte[] bytes = is.readAllBytes(); // 一次性读取所有的数据
```

#### FileOutputStream

文件字节输出流：FileOutputStream，文件可以不存在，会自动创建文件
换行："\r\n".getBytes()

会出错：os.write('你')

| 方法名                                                   | 说明                                                   |
| -------------------------------------------------------- | ------------------------------------------------------ |
| public FileOutputStream(File file)                       | 创建字节输出流管道与源文件对象接通（自动清空原有数据） |
| public FileOutputStream(File file，boolean append)       | 创建字节输出流管道与源文件对象接通，可追加数据         |
| public FileOutputStream(String filepath)                 | 创建字节输出流管道与源文件路径接通（自动清空原有数据） |
| public FileOutputStream(String filepath，boolean append) | 创建字节输出流管道与源文件路径接通，可追加数据         |
| public void write(int a)                                 | 写一个字节出去                                         |
| public void write(byte[] buffer)                         | 写一个字节数组出去                                     |
| public void write(byte[] buffer, int pos, int len)       | 写一个字节数组的一部分出去                             |



```java
InputStream is  = new FileInputStream("file-io-app/src/out04.txt");
OutputStream os = new FileOutputStream("file-io-app/src/out05.txt");

int len;
byte[] buffer = new byte[1024];
while ((len = is.read(buffer)) != -1) {
    os.write(buffer, 0, len);
}
System.out.println("复制完成了!");

os.close();
is.close();

```

#### 释放资源

| 方法名  | 说明                                                         |
| ------- | ------------------------------------------------------------ |
| flush() | 刷新流，还可以继续写数据                                     |
| close() | 关闭流，释放资源，但是在关闭之前会先刷新流，一旦关闭，就不能再写数据 |

### 文件字符流

JDK 8 不可以指定编码（JDK 16 可以）

#### FileReader

| 方法名                                 | 说明                                                         |
| -------------------------------------- | ------------------------------------------------------------ |
| public FileReader(File file)           | 创建字符输入流管道与源文件对象接通                           |
| public FileReader(String pathname)     | 创建字符输入流管道与源文件路径接通                           |
| FileReader(File file, Charset charset) | FileReader(String fileName, Charset charset)                 |
| public int read()                      | 每次读取一个字符返回，如果字符已经没有可读的返回 -1          |
| public int read(char[] buffer)         | 每次读取一个字符数组，返回读取的字符个数，如果字符已经没有可读的返回 -1 |

相关API：
1）newString(char[]):将char[]转换成String
2）new String(char[],off,len):将char[]的指定部分转换成String

```java
Reader fr = new FileReader("src\\ab.txt");

int len;
char[] buffer = new char[10];

while ((len = fr.read(buffer)) != -1) {
    System.out.print(new String(buffer, 0, len));
}
```



#### FileWriter

换行："\r\n"
不会出错：fw.write('你')

| 方法名                                                 | 说明                                                         |
| ------------------------------------------------------ | ------------------------------------------------------------ |
| public FileWriter(File file)                           | 覆盖模式，相当于流的指针在首端                               |
| public FileWriter(File file，boolean append)           | 追加模式，相当于流的指针在尾端                               |
| public FileWriter(String filepath)                     | 覆盖模式，相当于流的指针在首端                               |
| public FileWriter(String filepath，boolean append)     | 追加模式，相当于流的指针在尾端                               |
| FileWriter(File file, Charset charset, boolean append) | FileWriter(String fileName, Charset charset, boolean append) |
| void write(int c)                                      | 写一个字符                                                   |
| void write(char[] cbuf)                                | 写入一个字符数组                                             |
| void write(char[] cbuf, int off, int len)              | 写入字符数组的指定部分                                       |
| void write(String str)                                 | 写一个字符串                                                 |
| void write(String str, int off, int len)               | 写一个字符串的指定部分                                       |

相关APl：String类：toCharArray:将String转换成char[]

>注意：
>FileWriter使用后，必须要关闭(close)或刷新(flush），否则写入不到指定的文件！

```java
Reader fr = new FileReader("src\\ab.txt");
Writer fw = new FileWriter("src\\bc.txt", true);

int len;
char[] chars = new char[10];
while ((len = fr.read(chars)) != -1) {
    fw.write(chars, 0, len);
}
System.out.println("复制完成了!");

fw.close();
fr.close();

```

## 缓冲流

缓冲流属于装饰器类，特点是支持读写缓存

对比 InputStream，BufferedInputStream 会在内存中维护一个 8192 字节大小的缓存

- 如果缓存中没有足够的数据：那么 read() 函数会向操作系统内核请求数据，读取 8192 字节存储到缓存中，然后 read() 函数再从缓存中返回需要的数据量
- 如果缓存中有足够多的数据：read() 函数直接从缓存中读取数据，不再请求操作系统
- 读取同样多的数据，利用 BufferedInputStream，向操作系统内核请求数据的次数减少

向操作系统内核请求数据，需要使用系统调用，引起用户态和内核态的切换，是非常耗时的，所以尽量减少系统调用，会提高程序的性能
不过，如果 read() 函数每次请求的数据量都大于等于 8192 字节，那么 BufferedInputStream 就不起作用了



构造器

| 构造器                                       | 说明                                                         |
| -------------------------------------------- | ------------------------------------------------------------ |
| public BufferedInputStream(InputStream is)   | 可以把低级的字节输入流包装成一个高级的缓冲字节输入流，从而提高读数据的性能 |
| public BufferedOutputStream(OutputStream os) | 可以把低级的字节输出流包装成一个高级的缓冲字节输出流，从而提高写数据的性能 |

方法

| 缓冲输入流方法                  | 说明                                                         | 缓冲输出流方法                   | 说明                                                         |
| ------------------------------- | ------------------------------------------------------------ | -------------------------------- | ------------------------------------------------------------ |
| public BufferedReader(Reader r) | 可以把低级的字符输入流包装成一个高级的缓冲字符输入流管道，从而提高字符输入流读数据的性能 | public  BufferedWriter(Writer w) | 可以把低级的字符输出流包装成一个高级的缓冲字符输出流管道，从而提高字符输出流写数据的性能 |
| public String readLine()        | 读取一行数据返回，如果读取没有完毕，无行可读返回 null        | public  void newLine()           | 换行操作                                                     |

如下代码所示，如果文件中的数据大小是 8192 字节，那么，读取所有数据需要调用 8 次 read() 函数，但因为缓存的存在，所以仅需要向操作系统内核请求一次数据

```java
InputStream in = new FileInputStream("F:\\test-file\\in.txt");
InputStream bin = new BufferedInputStream(in);

int len;
byte[] buffer = new byte[1024];

while ((len = bin.read(buffer)) != -1) {
    System.out.println(new String(buffer, 0, len));
}
```

同理，针对 OutputStream，java.io 类库提供了 BufferedOutputStream，用来缓存写入的数据
当积攒到一定量（默认为 8192 字节）时，再一次性将其写入操作系统内核缓冲区，减少系统调用次数，提高程序的性能



## 转换流

将字节流转换为字符流：InputStreamReader、OutputStreamWriter

- 可以提取文件（GBK）的原始字节流，原始字节不会存在问题
- 然后把字节流以指定编码转换成字符输入流，这样字符输入流中的字符就不乱码了

构造器

| 输入转换流构造器                                         | 说明                                                     | 输出转换流构造器                                            | 说明                                                         |
| -------------------------------------------------------- | -------------------------------------------------------- | ----------------------------------------------------------- | ------------------------------------------------------------ |
| public InputStreamReader(InputStream is)                 | 把原始的字节流按照代码默认编码转换成字符输入流，几乎不用 | public  OutputStreamWriter(OutputStream os)                 | 把原始的字节输出流按照代码默认编码转换成字符输出流，几乎不用 |
| public InputStreamReader(InputStream is，String charset) | 把原始的字节流按照指定编码转换成字符输入流（重点）       | public  OutputStreamWriter(OutputStream os，String charset) | 把原始的字节输出流按照指定编码转换成字符输出流（重点）       |

```java
//读取GBK格式的文件
public static void main(String[] args) throws Exception {
    InputStream in = new FileInputStream("D:\\resources\\data.txt"); // 提取 GBK 文件的原始字节流
    Reader r = new InputStreamReader(in, "GBK");                     // 把原始字节流转换成字符输入流

    BufferedReader br = new BufferedReader(r);

    String line;
    while ((line = br.readLine()) != null){
        System.out.println(line);
    }
}

```

## 打印流

PrintStream 和 PrintWriter 可以将数据按照一定的格式，转化为字符串，写入到输出流，

PrintStream 和 PrintWriter 的区别

- 打印数据功能上是一模一样的，都是使用方便，性能高效（核心优势）
- 两者都不能追加（append），需要通过低级流来实现追加
- PrintStream 继承自字节输出流 OutputStream，支持写字节数据的方法（write）
- PrintWriter 继承自字符输出流 Writer，支持写字符数据出去（write）

| PrintStream 构造器                          | 说明                               | PrintWriter 构造器                       | 说明                               |
| ------------------------------------------- | ---------------------------------- | ---------------------------------------- | ---------------------------------- |
| public PrintStream(OutputStream os)         | 打印流直接通向字节输出流管道       | public  PrintWriter(OutputStream os)     | 打印流直接通向字节输出流管道       |
| public PrintStream(File f)                  | 打印流直接通向文件对象             | public  PrintWriter (Writer w)           | 打印流直接通向字符输出流管道       |
| public PrintStream(File f, Charset charset) | 打印流直接通向文件对象，并指定编码 | public  PrintWriter (File f)             | 打印流直接通向文件对象             |
| public PrintStream(String filepath)         | 打印流直接通向文件路径             | PrintWriter(File  file, Charset charset) | 打印流直接通向文件对象，并指定编码 |
| public void print(Xxx xx)                   | 打印任意类型的数据出去             | public  PrintWriter (String filepath)    | 打印流直接通向文件路径             |
|                                             |                                    | public void  print(Xxx xx)               | 打印任意类型的数据出去             |

```java
//System类的out方法调用的是 public static final PrintStream out
//可以指定打印流，让系统不在控制台输出
PrintStream ps = new PrintStream(new FileOutputStream("src\\ab.txt", true));
System.setOut(ps);
System.out.println("你好");

```

## 标准输入输出

在操作系统中，一般会有三个标准 I / O 系统：标准输入、标准输出、标准错误输出

- 标准输入对应 I / O 设备中的键盘，标准输出和标准错误输出对应 I / O 设备中的屏幕
- Java 中的标准输入为 System.in，它是一个定义在 System 类中的静态 InputStream 对象
- Java 中的标准输出和标准错误输出分别为 System.out 和 System.err，它们都是定义在 System 类中的 PrintStream 对象
  PrintStream 为装饰器类，需要嵌套 OutputStream 来使用，支持按照格式输出数据，待会会讲到

System.in、System.out、System.err 的使用示例如下所示

```java
Scanner s = new Scanner(System.in);

System.out.println("echo: " + s.nextLine());
System.err.println("echo: " + s.nextLine()); // System.err 显示的字符串为红色, 以表示出错
```



## 数据流

用来操作基本数据类型和字符串的（8 + String）
DataInputStream：将文件中存储的基本数据类型（byte、char、short、int、float、double 、boolean、long）和字符串写入内存的变量中
DataOutputStream：将内存中的基本数据类型和字符串的变量写出文件中

```java
// 输出
DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("F:\\我的文件\\data1.txt")));

dos.writeUTF("你好");
dos.writeBoolean(false);
dos.writeDouble(9.71);
dos.writeInt(82);

dos.close();

```

```java
// 输入
DataInputStream dis = new DataInputStream(new FileInputStream(new File("F:\\我的文件\\data1.txt")));

System.out.println(dis.readUTF());
System.out.println(dis.readBoolean());
System.out.println(dis.readDouble());
System.out.println(dis.readInt());

dis.close();

```

调用 DataOutputStream 的 readChar()、writeChar() 函数，我们也可以按字符为单位读取、写入数据
但跟字符流类不同的地方是，DataOutputStream 类一次只能处理一个字符，而字符流类可以处理 char 数组，并且字符流类提供的函数更多，功能更加丰富

## 对象流

ObjectInputStream 支持将从输入流中读取的数据反序列化为对象，ObjectOutputStream 支持将对象序列化之后写入到输出流，示例代码如下所示

```

```
