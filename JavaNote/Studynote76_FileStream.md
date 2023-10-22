# 文件处理

## File类

java.io.File 类，主要用于文件和目录的创建、查找和删除，不能对文件内容进行操作。

### 构造器

| 方法名                                   | 说明                                               |
| ---------------------------------------- | -------------------------------------------------- |
| public File(String pathname)             | 根据文件路径创建文件对象                           |
| public File(String parent, String child) | 从父路径名字符串和子路径名字符串创建文件对象       |
| public File(File parent, String child)   | 根据父路径对应文件对象和子路径名字符串创建文件对象 |

代码演示

```java
//方式1 new File(String pathname)
File file = new File("文件 / 文件夹 / 绝对路径 / 相对路径");
File file = new File("D:\\itheima\\a.txt"); // 绝对路径
File file = new File("a.txt");              // 相对路径

//方式2 new File(File parent,String child) //根据父目录文件+子路径构建
File folderName = new File("e:\\");
String fileName = "news2.txt";
//这里创建的file对象，只是java程序中的一个对象实例，不代表有这个文件了
//只有执行了createNewFile 方法，才会真正地在磁盘创建该文件
File file = new File(folderName, fileName);

//方式3 new File(String parent,String child) //根据父目录+子路径构建
String parentPath = "e:\\";
String fileName = "news4.txt";
```



### 常用方法

| 方法名                          | 说明                           | 方法名                          | 说明                                                         |
| ------------------------------- | ------------------------------ | ------------------------------- | ------------------------------------------------------------ |
| public boolean isDirectory()    | 判断 File 是否为文件夹         | public  boolean createNewFile() | 创建一个新的空的文件                                         |
| public boolean isFile()         | 判断 File 是否为文件           | public  boolean mkdir()         | 只能创建一级文件夹                                           |
| public boolean exists()         | 判断 File 是否存在             | public  boolean mkdirs()        | 可以创建多级文件夹                                           |
| public String getAbsolutePath() | 获取**绝对路径**               | public  boolean delete()        | 删除由此 File 所表示的**文件或空文件夹**，不走 windows 回收站 |
| public String getPath()         | 获取 File 定义时使用的**路径** | public  String[] list()         | 获取当前目录下所有的 "一级文件名称"                          |
| public String getName()         | 获取文件或文件夹的**名称**     | public  File[] listFiles()      | 获取当前目录下所有的 "一级文件对象"（要求指定目录必须存在）  |
| public String getParent()       | 获取文件或文件夹的**父目录**   | public  long lastModified()     | 获取文件最后修改的时间毫秒值                                 |
| boolean renameTo(File dest)     | 对文件重命名（不同路径会剪切） | public  long length()           | 获取**文件的大小**，不能获取目录的大小，单位：B              |

备注：

* mac系统不知道路径的可以String home = System.getProperty("user.home"); 然后再用 String filePath = home + "/Desktop/xxx/xxx.txt";
* delete() ：即使文件被占用，也会删除，不走回收站，方法默认只能删除文件和空文件夹
* 调用 `createNewFile()` 方法，需要捕获 IOException

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

//如果返回-1 , 表示读取完毕
while ((len = is.read(buffer)) != -1) {
    System.out.println(new String(buffer, 0, len)); // 读多少数据, 倒多少数据
}

is.close();

//另一种方式
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

文件拷贝

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



## RandomAccessFile

[Java File：IO 流的起点与终点 | 二哥的Java进阶之路 (tobebetterjavaer.com)](https://tobebetterjavaer.com/io/file-path.html#randomaccessfile)



















```java
import org.junit.jupiter.api.Test;

import java.io.*;


class FileCreate {//演示创建文件
    public static void main(String[] args) {
    }

    //方式1 new File(String pathname)
    @Test
    public void create01() {
        String filePath = "e:\\news1.txt";
        File file = new File(filePath);
    
        try {
            file.createNewFile();
            System.out.println("文件创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    //方式2 new File(File parent,String child) //根据父目录文件+子路径构建
    //e:\\news2.txt
    @Test
    public  void create02() {
        File folderName = new File("e:\\");
        String fileName = "news2.txt";
        //这里的file对象，在java程序中，只是一个对象
        //只有执行了createNewFile 方法，才会真正的，在磁盘创建该文件
        //file对象创建不代表有这个文件了
        File file = new File(folderName, fileName);
    
        try {
            file.createNewFile();
            System.out.println("创建成功~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //方式3 new File(String parent,String child) //根据父目录+子路径构建
    @Test
    public void create03() {
        //String parentPath = "e:\\";
        String parentPath = "e:\\";
        String fileName = "news4.txt";
        File file = new File(parentPath, fileName);
    
        try {
            file.createNewFile();
            System.out.println("创建成功~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
获取文件的相关信息
getName、 getAbsolutePath、getParent.length. exists. isFile、isDirectory
 */

class FileInformation {
    public static void main(String[] args) {

    }
    
    //获取文件的信息
    @Test
    public void info() {
        //先创建文件对象
        File file = new File("e:\\news1.txt");
    
        //调用相应的方法，得到对应信息
        System.out.println("文件名字=" + file.getName());
        //getName、getAbsolutePath、getParent、length、exists、isFile、isDirectory
        System.out.println("文件绝对路径=" + file.getAbsolutePath());
        System.out.println("文件父级目录=" + file.getParent());
        System.out.println("文件大小(字节)=" + file.length());
        System.out.println("文件是否存在=" + file.exists());//T
        System.out.println("是不是一个文件=" + file.isFile());//T
        System.out.println("是不是一个目录=" + file.isDirectory());//F


    }
}
/*
目录的操作和文件删除
mkdir创建一级目录、mkdirs创建多级目录、delete删除空目录或文件
 */

class Directory_ {
    public static void main(String[] args) {    }

    //判断 d:\\news1.txt 是否存在，如果存在就删除
    @Test
    public void m1() {
    
        String filePath = "e:\\news1.txt";
        File file = new File(filePath);
        if (file.exists()) System.out.println((file.delete()) ? filePath +"删除成功" : filePath +"删除失败");
        else System.out.println("该文件不存在...");
    }
    
    //判断 D:\\demo02 是否存在，存在就删除，否则提示不存在
    //这里我们需要体会到，在java编程中，目录也被当做文件
    @Test
    public void m2() {
    
        String filePath = "D:\\demo02";
        File file = new File(filePath);
        if (file.exists()) System.out.println((file.delete()) ? filePath +"删除成功" : filePath +"删除失败");
        else System.out.println("该目录不存在...");
    
    }
    
    //判断 D:\\demo\\a\\b\\c 目录是否存在，如果存在就提示已经存在，否则就创建
    @Test
    public void m3() {
    
        String directoryPath = "D:\\demo\\a\\b\\c";
        File file = new File(directoryPath);
        if (file.exists()) {
            System.out.println(directoryPath + "存在..");
        } else {
            if (file.mkdirs()) { //创建一级目录使用mkdir() ，创建多级目录使用mkdirs()
                System.out.println(directoryPath + "创建成功..");
            } else {
                System.out.println(directoryPath + "创建失败...");
            }
        }
    
    }
}



//演示FileInputStream的使用(字节输入流 文件--> 程序)
class FileInputStream_ {
    public static void main(String[] args) {

    }
    
    /**
     * 演示读取文件...
     * 单个字节的读取，效率比较低
     * 改进 -> 使用 read(byte[] b)
     */
    @Test
    public void readFile01() {
        String filePath = "e:\\hello.txt";
        int readData = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取 文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取一个字节的数据。 如果没有输入可用，此方法将阻止。
            //如果返回-1 , 表示读取完毕
            while ((readData = fileInputStream.read()) != -1) {
                System.out.print((char)readData);//转成char显示
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    }
    
    /**
     * 使用 read(byte[] b) 读取文件，提高效率
     *
     * 第二种方法的目的是为了提高文件读取的效率，byte数组是可以自定义的数组长度，定义后可将其长度
     * 放在创建的string类型参数中，并返回所读取的文件数据的内容
     */
    @Test
    public void readFile02() {
        String filePath = "e:\\hello.txt";
        //字节数组
        byte[] buf = new byte[8]; //一次读取8个字节.
        int readLen = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取 文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取最多b.length字节的数据到字节数组。 此方法将阻塞，直到某些输入可用。
            //如果返回-1 , 表示读取完毕
            //如果读取正常, 返回实际读取的字节数
            while ((readLen = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));//显示
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    }
}
/*
FileOutputStream继承OutputStream类
 */

class FileOutputStream01 {
    public static void main(String[] args) {

    }
    
    /**
     * 演示使用FileOutputStream 将数据写到文件中,
     * 如果该文件不存在，则创建该文件
     */
    @Test
    public void writeFile() {
    
        //创建 FileOutputStream对象
        String filePath = "e:\\a.txt";
        FileOutputStream fileOutputStream = null;
        try {
            //得到 FileOutputStream对象 对象
            //说明
            //1. new FileOutputStream(filePath) 创建方式，当写入内容是，会覆盖原来的内容
            //2. new FileOutputStream(filePath, true) 创建方式，当写入内容是，是追加到文件后面
            fileOutputStream = new FileOutputStream(filePath, true);
            //写入一个字节
            //fileOutputStream.write('H');//
            //写入字符串
            String str = "hsp,world!";
            //str.getBytes() 可以把 字符串-> 字节数组
            //fileOutputStream.write(str.getBytes());
            /*
            write(byte[] b, int off, int len) 将 len字节从位于偏移量 off的指定字节数组写入此文件输出流
             */
            fileOutputStream.write(str.getBytes(), 0, 3);
    
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class FileCopy {
    public static void main(String[] args) {
        //完成 文件拷贝，将 e:\\Koala.jpg 拷贝 c:\\
        //思路分析
        //1. 创建文件的输入流 , 将文件读入到程序
        //2. 创建文件的输出流， 将读取到的文件数据，写入到指定的文件.
        //3.在完成程序时，应该是读取部分数据就写入到指定文件
        //使用循环实现
        String srcFilePath = "e:\\Koala.jpg";
        //如果是拷贝有目录的话，得先创建它的上级目录，通过getParentFile()方法得到上级目录，然后创建
        String destFilePath = "e:\\Koala3.jpg";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
    
            fileInputStream = new FileInputStream(srcFilePath);
            //不用true是因为这次只创建了一个文件，while循环中获取了原文件的所有信息后才进行创建，不存在覆盖
            fileOutputStream = new FileOutputStream(destFilePath);
            //定义一个字节数组,提高读取效果
            byte[] buf = new byte[1024];
            int readLen = 0;
            while ((readLen = fileInputStream.read(buf)) != -1) {
                //读取到后，就写入到文件 通过 fileOutputStream即，是一边读，一边写
                //byte数组容量1024个，读不满的话有上一次残留下来的数组内容会导致乱码，
                // 第一次就读不满的话还有0导致多出来空格
                fileOutputStream.write(buf, 0, readLen);//一定要使用这个方法
    
            }
            System.out.println("拷贝ok~");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭输入流和输出流，释放资源
                //fileInputStream、fileOutputStream 是对象，进入finally的时候表示它们已经使用完了，但本身不为空
                // io流打开后不关会常驻内存；流等于空相当于对象都没创建成功，关闭时会出现“空指针异常”，因此加入判断机制
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}



class FileReader_ {
    public static void main(String[] args) {
    }

    /**
     * 单个字符读取文件
     */
    @Test
    public void readFile01() {
        String filePath = "e:\\story.txt";
        FileReader fileReader = null;
        int data = 0;
        //1. 创建FileReader对象
        try {
            fileReader = new FileReader(filePath);
            //循环读取 使用read, 单个字符读取
            while ((data = fileReader.read()) != -1) {
                //read返回的是文件中的字符或者到达文件末尾的-1，
                // 所以用char没办法接收，但是字符本质上就是一个数字，所以用int接收两种情况
                //当接收到的不是-1时，转成字符并输出
                System.out.print((char) data);
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 字符数组读取文件
     */
    @Test
    public void readFile02() {
        System.out.println("~~~readFile02 ~~~");
        String filePath = "e:\\story.txt";
        FileReader fileReader = null;
    
        int readLen = 0;
        char[] buf = new char[8];
        //1. 创建FileReader对象
        try {
            fileReader = new FileReader(filePath);
            //循环读取 使用read(buf), 返回的是实际读取到的字符数
            //如果返回-1, 说明到文件结束
            while ((readLen = fileReader.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

//输出流的两个细节：1.到底选择覆盖模式还是追加模式，2.一定要关闭或刷新，不然字符未写入文件
class FileWriter_ {
    public static void main(String[] args) {

        String filePath = "e:\\note.txt";
        //创建FileWriter对象
        FileWriter fileWriter = null;
        char[] chars = {'a', 'b', 'c'};
        try {
            fileWriter = new FileWriter(filePath);//默认是覆盖写入
//            3) write(int):写入单个字符
            fileWriter.write('H');
//            4) write(char[]):写入指定数组
            fileWriter.write(chars);
//            5) write(char[],off,len):写入指定数组的指定部分
            fileWriter.write("青岛北站".toCharArray(), 0, 3);
//            6) write（string）：写入整个字符串
            fileWriter.write(" 你好北京~");
//            7) write(string,off,len):写入字符串的指定部分
            fileWriter.write("济局青段", 0, 2);
            //在数据量大的情况下，可以使用循环操作.


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
    
            //对应FileWriter , 一定要关闭流，或者flush才能真正的把数据写入到文件
            //直接理解为writer底层没有写入数据的方法，需要通过close或flush方法来调用到父类的方法
            /*
                看看代码：无论是close()还是flush()，最终都会进入StreamEncoder类的writeBytes()方法
                private void writeBytes() throws IOException {
                    this.bb.flip();
                    int var1 = this.bb.limit();
                    int var2 = this.bb.position();
    
                    assert var2 <= var1;
    
                    int var3 = var2 <= var1 ? var1 - var2 : 0;
                    if (var3 > 0) {
                        if (this.ch != null) {
                            assert this.ch.write(this.bb) == var3 : var3;
                        } else {
                            this.out.write(this.bb.array(), this.bb.arrayOffset() + var2, var3);
                        }
                    }
    
                    this.bb.clear();
                }
             */
            try {
                //fileWriter.flush();
                //关闭文件流，等价 flush() + 关闭
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    
        }
    
        System.out.println("程序结束...");


    }
}

```