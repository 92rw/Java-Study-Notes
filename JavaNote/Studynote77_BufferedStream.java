import java.io.*;

public class Studynote78_BufferedStream {
}
/*
缓冲处理流
BufferedReader和 BufferedWriter属于字符流，是按照字符来读取数据的
 在关闭处理流/包装流的时候，只需要关闭外层流即可 ->底层会自动会去关闭包装的节点流

BufferedInputStream 和 BufferedOutputStream 是字节流，在创建BufferedInputStream时，会创建一个内部缓冲区数组
BufferedOutputStream是实现缓冲的输出流，可以将多个字节写入底层输出流中，而不必对每次字节写入调用底层系统

字符流尽量操作文本文件，因为二进制文件可能会出现损失
处理流的reader属性是一个向上转型的节点流，在处理流的方法里调用的还是节点流的方法，只不过对这个方法进行了补充优化

现在练习用throw可以精简代码，但如果真的出现异常，则下面的代码就不能继续执行了，包括关闭流语句（保证写入的内容保存）
所以实际使用时还是要用try-catch-finally，才能确保不管有没有异常都能关闭流。

如果读取的文件不存在，那么输入流就是null了，此时finally语句中不做判断就关闭，会报空指针异常

还有一个比较重要的流，RandomAccessFile随机文件流，这个流同时实现了DataInput和DataOutput
 */

class BufferedReader_ {
    public static void main(String[] args) throws Exception {

        String filePath = "e:\\a.java";
        //创建 bufferedReader 对象，构造器中传入节点流
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        //读取
        String line; //按行读取, 效率高
        //说明
        //1. bufferedReader.readLine() 是按行读取文件
        //2. 当返回null 时，表示文件读取完毕
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        //关闭流, 这里注意，只需要关闭 BufferedReader ，因为底层会自动的去关闭 节点流FileReader
        bufferedReader.close();
        //对应源代码为
        /*
            public void close() throws IOException {
                synchronized (lock) {
                    if (in == null)
                        return;
                    try {
                        in.close();//in 就是我们传入的 new FileReader(filePath), 关闭了.
                    } finally {
                        in = null;
                        cb = null;
                    }
                }
            }

         */
    }
}

class BufferedWriter_ {
    public static void main(String[] args) throws IOException {
        String filePath = "e:\\ok.txt";
        //创建BufferedWriter对象
        //说明:是否覆盖源文件，是在创建节点流时指定
        //1. new FileWriter(filePath, true) 表示以追加的方式写入
        //2. new FileWriter(filePath) , 表示以覆盖的方式写入
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write("红岛站");
        //插入一个和系统相关的换行
        bufferedWriter.newLine();//用换行符不安全，不同的系统对于换行的定义不同，可能会导致换行失败
        bufferedWriter.write("青岛西站");
        bufferedWriter.newLine();
        bufferedWriter.write("董家口站");
        bufferedWriter.newLine();

        //说明：关闭外层流即可 ， 传入的 new FileWriter(filePath) ,会在底层关闭
        bufferedWriter.close();
    }
}

class BufferedCopyChar {

    public static void main(String[] args) {
        //说明
        //1. BufferedReader 和 BufferedWriter 按照字符进行操作
        //2. 不要去操作 二进制文件[声音，视频，doc, pdf等], 可能造成文件损坏
        //二进制文件处理应使用字节流BufferedInputStream和BufferedOutputStream
        String srcFilePath = "e:\\a.java";
        String destFilePath = "e:\\a2.java";
//        String srcFilePath = "e:\\客车8416次通过沙岭庄站.avi";
//        String destFilePath = "e:\\客车8416次通过沙岭庄站-副本.avi";
        BufferedReader br = null;
        BufferedWriter bw = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(srcFilePath));
            bw = new BufferedWriter(new FileWriter(destFilePath));

            //说明: 通过循环中的 readLine 方法读取一行内容，但是没有换行
            while ((line = br.readLine()) != null) {
                //每读取一行，就写入
                bw.write(line);
                //插入一个换行
                bw.newLine();
            }
            System.out.println("拷贝完毕...");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(br != null) {
                    br.close();
                }
                if(bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

//包装流中的缓冲字节流
//字节操作在控制台输出时会产生乱码，但是输出到文件不会乱码。原因：一次性读取的字节不够，
// 如果数组中设置最大读取1024个字节，在控制台输出时出现乱码的几率就小
class BufferedCopyByte {
    public static void main(String[] args) {

//        String srcFilePath = "e:\\Koala.jpg";
//        String destFilePath = "e:\\Koala-Copy.jpg";
//        String srcFilePath = "e:\\客车8416次通过沙岭庄站.avi";
//        String destFilePath = "e:\\客车8416次通过沙岭庄站-副本.avi";
        String srcFilePath = "e:\\a.java";
        String destFilePath = "e:\\a3.java";

        //创建BufferedOutputStream对象BufferedInputStream对象
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            //因为 FileInputStream  是 InputStream 子类
            bis = new BufferedInputStream(new FileInputStream(srcFilePath));
            bos = new BufferedOutputStream(new FileOutputStream(destFilePath));

            //循环的读取文件，并写入到 destFilePath
            byte[] buff = new byte[1024];
            int readLen = 0;
            //这里调用的是 FileInputStream 的 read(byte[] buff) 方法
            //当返回 -1 时，就表示文件读取完毕
            while ((readLen = bis.read(buff)) != -1) {
                bos.write(buff, 0, readLen);
            }

            System.out.println("文件拷贝完毕~~~");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //关闭流 , 关闭外层的处理流即可，底层会去关闭节点流
            try {
                if(bis != null) {
                    bis.close();
                }
                if(bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

//判断目录和文件是否存在，如果目录不存在就新创建，如果文件不存在就新建并赋值
class EnsureFileExist{
    public static void main(String[] args) throws IOException {
        //判断目录是否存在
        String directoryPath = "D:\\mytemp";
        File path = new File(directoryPath);
        if (!path.exists()) {
            path.mkdirs();//如果是单级目录，可改用mkdirs()方法
            System.out.println("创建 " + directoryPath + " 成功");
        }

        //判断文件是否存在
        String fileName = "hello.txt";
        File file = new File(path,fileName);
        if (file.exists()) {
            System.out.println(fileName + "已存在");
        } else {
            BufferedWriter bf = new BufferedWriter(new FileWriter(directoryPath + "\\" + fileName));
            bf.write("Hello, world!");
            bf.close();

            System.out.println(fileName + " 创建成功");
        }
    }
}

class BufferedReadFile{
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\橄榄山Revit.txt";
        String content = "";
        int Line = 0;
        //如果需要处理乱码，加入转换流 new BufferedReader(new InputStreamReader (new FileInputStream(filePath),"gbk"));
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        
        while ((content = br.readLine()) != null){
            System.out.println((++Line) + "\t" + content);
        }
        br.close();
    }
}