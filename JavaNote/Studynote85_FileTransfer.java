import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/*
将客户端的文件，通过网络拷贝到服务端

1.编写一个服务端，和一个客户端
2.服务器端在8888端口监听
3.客户端连接到服务端，发送一个文件
4.服务器端接收到客户端发送的文件，保存到src下，发送“收到文件”再退出
5.客户端接收到服务端发送的“收到文件"并显示，然后退出
注：使用 StreamUtil 类简化工程量 -> 边读边写会造成访问频繁的问题

思路：（客户端）读取图片->转换成字节数组->传入socket-> 设置结束标志->（服务器端）接收socket->传入字节数组->转换成图片->发送成功信息->
     （客户端）接收信息->显示信息

备注：缓存中满了会自动写入，没满就存在缓存里，需要flush或close，写进文件中去
可以自己在流的后面加一个结束标识符比如-1来结束流，然后在另一端检查
完成内容写入后：包装流flush -> socket.shutdownOutput() -> 包装流关闭。注意不要提前关了socket的包装流，
 */

class TCPFileUploadServer {
    public static void main(String[] args) throws Exception {

        //1. 服务端在本机监听8888端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端在8888端口监听....");
        //2. 等待连接
        Socket socket = serverSocket.accept();


        //3. 读取客户端发送的数据
        //   通过Socket得到输入流
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtil.streamToByteArray(bis);
        //4. 将得到 bytes 数组，写入到指定的路径，就得到一个文件了
        String destFilePath = "src\\abc.mp4";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFilePath));
        bos.write(bytes);
        bos.close();

        // 向客户端回复 "收到图片"
        // 通过socket 获取到输出流(字符)
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("收到图片");
        writer.flush();//把内容刷新到数据通道
        socket.shutdownOutput();//设置写入结束标记

        //关闭其他资源
        writer.close();
        bis.close();
        socket.close();
        serverSocket.close();
    }
}

class TCPFileUploadClient {
    public static void main(String[] args) throws Exception {

        //客户端连接服务端 8888，得到Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        //创建读取磁盘文件的输入流
        //String filePath = "e:\\qie.png";
        String filePath = "e:\\abc.mp4";
        BufferedInputStream bis  = new BufferedInputStream(new FileInputStream(filePath));

        //bytes 就是filePath对应的字节数组
        byte[] bytes = StreamUtil.streamToByteArray(bis);

        //通过socket获取到输出流, 将bytes数据发送给服务端
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);//将文件对应的字节数组的内容，写入到数据通道
        bis.close();
        socket.shutdownOutput();//设置写入数据的结束标记

        //=====接收从服务端回复的消息=====

        InputStream inputStream = socket.getInputStream();
        //使用StreamUtils 的方法，直接将 inputStream 读取到的内容 转成字符串
        String s = StreamUtil.streamToString(inputStream);
        System.out.println(s);


        //关闭相关的流
        inputStream.close();
        bos.close();
        socket.close();
    }
}

/**
 * 此类用于演示关于流的读写方法
 */
class StreamUtil {
    /**
     * 功能：将输入流转换成byte[]， 即可以把文件的内容读入到byte[]
     * @param is
     * @return
     * @throws Exception
     */
    public static byte[] streamToByteArray(InputStream is) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//创建输出流对象
        byte[] b = new byte[1024];//字节数组
        int len;
        while((len=is.read(b))!=-1){//循环读取
            bos.write(b, 0, len);//把读取到的数据，写入bos
        }
        byte[] array = bos.toByteArray();//然后将bos 转成字节数组
        bos.close();
        return array;
    }
    /**
     * 功能：将InputStream转换成String
     * @param is
     * @return
     * @throws Exception
     */

    public static String streamToString(InputStream is) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder= new StringBuilder();
        String line;
        while((line=reader.readLine())!=null){
            builder.append(line+"\r\n");
        }
        return builder.toString();

    }

}

/*
练习3
（1）编写客户端程序和服务器端程序
（2）客户端可以输入一个音乐文件名，比如高山流水，服务端收到音乐名后，可以给客户端返回这个音乐文件，
如果服务器没有这个文件，返回一个默认的音乐即可
（3）客户端收到文件后，保存到本地e:\\
（4）该程序可以使用StreamUtil

本质：指定下载文件的应用
 */

class Homework03Server {
    public static void main(String[] args) throws Exception {

        //1 监听 12306 端口
        ServerSocket serverSocket = new ServerSocket(12306);
        //2.等待客户端连接
        System.out.println("服务端，在12306端口监听，等待下载文件");
        Socket socket = serverSocket.accept();
        //3.读取 客户端发送要下载的文件名
        //  注意：1）使用了while读取文件名，是考虑将来客户端发送的数据较大的情况
        //       2）获取文件名应当使用字符流，此处用字节流仅作演示
        InputStream inputStream = socket.getInputStream();
        byte[] b = new byte[1024];
        int len = 0;
        String downLoadFileName = "";
        while ((len = inputStream.read(b)) != -1) {
            downLoadFileName += new String(b, 0 , len);
        }
        System.out.println("客户端希望下载文件名=" + downLoadFileName);

        //服务器上有两个文件, 无名.mp3 高山流水.mp3
        //如果客户下载的是 高山流水 我们就返回该文件，否则一律返回 无名.mp3
        //备注：File 类中有方法可以返回目录下的File数组，可以遍历目录，还可以判断是否存在、数据长度、文件路径、名字等

        String resFileName = "";
        if("高山流水".equals(downLoadFileName)) {
            resFileName = "src\\高山流水.mp3";
        } else {
            resFileName = "src\\无名.mp3";
        }

        //4. 创建一个输入流，读取文件
        BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(resFileName));

        //5. 使用工具类StreamUtil ，读取文件到一个字节数组
        byte[] bytes = StreamUtil.streamToByteArray(bis);
        //6. 得到Socket关联的输出流
        BufferedOutputStream bos =
                new BufferedOutputStream(socket.getOutputStream());
        //7. 写入到数据通道，返回给客户端
        //用 BufferedOutputStream 可以不用flush的，但是缓冲流不能close，否则会报错
        bos.write(bytes);
        socket.shutdownOutput();//很关键.

        //8 关闭相关的资源
        bis.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
        System.out.println("服务端退出...");

    }
}

class Homework03Client {
    public static void main(String[] args) throws Exception {


        //1. 接收用户输入，指定下载文件名
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入下载文件名");
        String downloadFileName = scanner.next();

        //2. 客户端连接服务端，准备发送
        Socket socket = new Socket(InetAddress.getLocalHost(), 12306);
        //3. 获取和Socket关联的输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(downloadFileName.getBytes());
        //设置写入结束的标志
        socket.shutdownOutput();
        //网友反馈：1）outputstream必须flush或者close，仅仅shutdownoutput没用
        // 2）循环下载的关键点是：在循环内关掉socket和所有流，再重新生成连接和流。

        //4. 读取服务端返回的文件(字节数据)
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtil.streamToByteArray(bis);
        //5. 得到一个输出流，准备将 bytes 写入到磁盘文件
        //另一种思路：新建一个socket专门传送服务器端返回的文件名，给下载到的文件指定命名
        String filePath = "e:\\" + downloadFileName + ".mp3";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        bos.write(bytes);

        //6. 关闭相关的资源
        bos.close();
        bis.close();
        outputStream.close();
        socket.close();

        System.out.println("客户端下载完毕，正确退出..");
    }
}

//字符流的写入结束才需要newLine结束+flush刷新，字节流直接shutdownOutput
