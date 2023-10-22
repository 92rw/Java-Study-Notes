import java.io.*;
import java.util.Scanner;

public class Studynote80_StandardStream {
}
/*
流的类型：只要记住大体分为 输入流和输出流，根据流的类型分类：字节流和字符流，根据流的传输类型：节点流，处理流，对象流，标准输入/输出流，转换流

标准流
	                    类型	   默认设备
System.in标准输入	InputStream	    键盘
System.out标准输出	PrintStream	    显示器

打印流（只有输出流，没有输入流）：PrintStream字节流和PrintWriter字符流
 */

class InputAndOutput {
    public static void main(String[] args) {
        //System 类 的 public final static InputStream in = null;
        // System.in 编译类型   InputStream
        // System.in 运行类型   BufferedInputStream
        // 表示的是标准输入 键盘
        System.out.println(System.in.getClass());

        //1. System.out public final static PrintStream out = null;
        //2. 编译类型 PrintStream
        //3. 运行类型 PrintStream
        //4. 表示标准输出 显示器
        System.out.println(System.out.getClass());

        System.out.println("System.out 方法将数据输出到显示器");

        Scanner scanner = new Scanner(System.in);
        System.out.println("传统的方法，Scanner是从标准输入System.in 获取键盘数据");
        String next = scanner.next();
        System.out.println("scanner获取到的 next=" + next);
    }
}

//演示PrintStream （字节打印流/输出流）

class PrintStream_ {
    public static void main(String[] args) throws IOException {

        PrintStream out = System.out;
        //System.out是System类的静态属性，指向PrintStream类的对象。在底层其实被赋值了；final对类的要求是地址不能变，里面的值可以更改
        // initializeSystemClass()方法里可以看到，只是赋值具体过程调用的native本地方法，即C++方法无法获取

        //在默认情况下，PrintStream 输出数据的位置是 标准输出，即显示器
        /*源码
             public void print(String s) {
                if (s == null) {
                    s = "null";
                }
                write(s);
            }

         */
        out.print("john, hello");

        //这里out.print 和 out.write 调用的 write 方法不一样，out.print 方法调用的write 是被private修饰的，
        // 而这里直接调用的write是public，因此调用write进行打印/输出时需要转为byte[]数组传入
        //打印Sting时,底层调用的是BufferedWriter的wirte(Sting)方法，但是这个方法是私有的
        out.write("约翰,你好".getBytes());
        out.close();

        //我们可以去修改打印流输出的位置/设备
        //1. 输出修改成到 "e:\\f1.txt"
        //2. "伊万，你好" 就会输出到 e:\f1.txt
        //3. public static void setOut(PrintStream out) {
        //        checkIO();
        //        setOut0(out); // native 方法，修改了out
        //   }
        System.setOut(new PrintStream("e:\\f1.txt"));//构造器中传入文件位置
        System.out.println("伊万，你好");
        //要改回来就再次调用那个setOut()方法，把上面的out传进去，因为上面那个out对象是输出到控制台
    }
}

class PrintWriter_ {
    public static void main(String[] args) throws IOException {
        //可以指定打印位置
        //PrintWriter printWriter = new PrintWriter(System.out);
        PrintWriter printWriter = new PrintWriter(new FileWriter("e:\\f2.txt"));
        printWriter.print("hi, 北京你好~~~~");
        //flush + 关闭流, 才会将数据写入到文件..
        //不只是输出到文件，就算是打印到控制台也是要 flush 刷新一下
        printWriter.close();

    }
}

/*转换流InputStreamReader和OutputStreamWriter：将字节流转换为字符流
单用字符流，他默认使用的是我们之前ide设置的（默认UTF-8），不能指定，只能改文件或ide。转换流可以指定更灵活

>介绍
1.InputStreamReader:Reader的子类，可以将InputStream（字节流）包装成Reader（字符流）
2.OutputStreamWriter:Writer的子类，实现将OutputStream（字节流）包装成Writer（字符流）
3.当处理纯文本数据时，如果使用字符流效率更高，并且可以有效解决中文问题，所以建议将字节流转换成字符流
4.可以在使用时指定编码格式（比如utf-8，gbk，gb2312，IS08859-1等）

编码转换：
构造器InputStreamReader(InputStream,Charset)传入字节流、编码方式两种参数，
构造器OutputStreamReader(OutputStream,Charset)传入字节流、编码方式两种参数，

 */
//演示使用转换流解决中文乱码问题：编程将字节流 FileInputStream 包装成（转换成）字符流 InputStreamReader
// 对文件进行读取（按照utf-8/gbk格式)，进而包装成BufferedReader
class InputStreamReader_ {
    public static void main(String[] args) throws IOException {
        //读取gbk格式的文件
        String filePath = "e:\\a.txt";
        //解读
        //1. 把 FileInputStream 转成 InputStreamReader
        //2. 指定编码 gbk
        //InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "gbk");
        //3. 把 InputStreamReader 传入 BufferedReader
        //BufferedReader br = new BufferedReader(isr);

        //将2 和 3 合在一起
        BufferedReader br = new BufferedReader(new InputStreamReader(
                                     new FileInputStream(filePath), "gbk"));

        //4. 读取
        String s = br.readLine();
        System.out.println("读取内容=" + s);
        //5. 关闭外层流
        br.close();
    }
}
//演示 OutputStreamWriter 使用：把FileOutputStream 字节流，转成字符流 OutputStreamWriter
// 指定输出的编码 utf-8/utf8
class OutputStreamWriter_ {
    public static void main(String[] args) throws IOException {
        String filePath = "e:\\hsp.txt";
        String charSet = "utf-8";//如果要输出的字符没有中文，最终也可能显示为别的格式
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), charSet);
        osw.write("按照 " + charSet + " 格式输出字符");
        osw.close();
        System.out.println("按照 " + charSet + " 保存文件成功~");
    }
}

