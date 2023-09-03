# Socket

[使用外部工具类简化操作](https://www.hutool.cn/docs/#/http/概述)

[HTTP编程](https://www.liaoxuefeng.com/wiki/1252599548343744/1319099982413858)

1. 套接字（Socket）开发网络应用程序被广泛采用，以至于成为事实上的标准
2. 网络通信其实就是Socket间的通信，通信的两端都要有Socket，是两台机器间通信的端点
3. 一个Socket就是由IP地址和端口号（范围是0～65535）组成，可以把Socket简单理解为IP地址加端口号
4. Socket允许程序把网络连接当成一个流，数据在两个Socket间通过IO传输
5. 一般主动发起通信的应用程序属客户端，等待通信请求的为服务端

Socket编程分成两类：TCP编程（可靠），UDP编程（不可靠）

### netstat 指令

```powershell
netstat -an #查看当前主机网络情况，包括端口监听情况和网络连接情况
netstat -an | more #可以分页显示

netstat -anb #查看端口上运行的程序（需要管理员权限）
```

说明：

* Listening表示某个端口在监听
* 如果有一个外部程序（客户端）连接到该端口，就会显示一条连接信息
* 要求在dos控制台下执行，可以输入 Ctrl+C 退出命令行

当客户端连接到服务端后，实际上客户端也是通过一个端口和服务端进行通讯的。这个端口是TCP/IP分配的随机数值

在API中，socket无参构造创建后，调用bind方法为其绑定端口和IP，传入的是指定的IP端口类，其他有参构造都是自动分配

socket 创建后，直接打印输出该对象就可以在 local-port 查看其在本地是占用的哪个端口

其他说明


## TCP网络通信编程

基本介绍

1. 基于客户端一服务端的网络通信

2. 底层使用的是TCP/IP协议

3. 应用场景举例：客户端发送数据，服务端接受并显示

4. 基于Socket的TCP编程

TCP协议：传输控制协议

1. 使用TCP协议前，须先建立TCP连接，形成传输数据通道
2. 传输前，采用“三次握手“方式，是可靠的
3. TCP协议进行通信的两个应用进程：客户端、服务端
4. 在连接中可进行大数据量的传输
5. 传输完毕，需释放已建立的连接，效率低

使用说明

TCP是一种基于流的协议，因此，Java标准库使用`InputStream`和`OutputStream`来封装Socket的数据流，以流的形式写入数据的时候，并不是一写入就立刻发送到网络，而是先写入内存缓冲区，直到缓冲区满了以后，才会一次性真正发送到网络，这样设计的目的是为了提高传输效率。如果缓冲区的数据很少，而我们又想强制把这些数据发送到网络，就必须调用`flush()`强制把缓冲区数据发送出去。



字节流传输

```java
/*
案例1（使用字节流）
1.编写一个服务器端，和一个客户端
2.服务器端在9999端口监听
3.客户端连接到服务器端，发送"hello，server"，然后退出
4.服务器端接收到客户端发送的信息，输出，并退出

注意:1、一定要先运行服务器端 不然连接不上
2、连接成功后不要再次运行客户端，否则会抛出拒绝连接异常，如果要再次运行客户端连接服务端，就重新运行服务端程序，在运行客户端程序，就不会抛出拒绝连接异常
3、一个数据源不能同时有两个流在操作，需要等输入流结束后才能执行输出流的代码，此处使用结束标记 socket.shutdownOutput();
 */
class SocketTCP01Server {
    public static void main(String[] args) throws IOException {//服务器端
        //思路
        //1. 在本机 的9999端口监听, 等待连接
        //   细节: 要求在本机没有其它服务在监听9999
        //   细节：这个 ServerSocket 可以通过 accept() 返回多个Socket[多个客户端连接服务器的并发]
        // 此处报异常的原因：因为9999端口可能不存在，或者被其他程序占用
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端，在9999端口监听，等待连接..");
        
        //2. 当没有客户端连接9999端口时，程序会 阻塞, 等待连接
        //   如果有客户端连接，则会返回Socket对象，程序继续

        Socket socket = serverSocket.accept();

        System.out.println("服务端 socket =" + socket.getClass());
        
        //3. 通过socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
        InputStream inputStream = socket.getInputStream();
        
        //4. IO读取
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1) {//字节流 一个个字节所以不能等于-1 字符流abcd不能为null
            System.out.println(new String(buf, 0, readLen));//根据读取到的实际长度，显示内容.
        }
        //5.获取输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello, client\n你好北京".getBytes());
        //   设置结束标记
        socket.shutdownOutput();
        
        //注意：socket没有文件里面的结束标志eof，是不会返回-1的。因此服务器端socket此时仍在监听状态
        //6.关闭流和socket
        outputStream.close();
        inputStream.close();
        socket.close();//Socket用于和客户端连接
        serverSocket.close();//ServerSocket 用于监听端口可以创建很多 socket。因此也需要关闭
    }
}

class SocketTCP01Client {//客户端
    public static void main(String[] args) throws IOException {
        //思路
        //1. 连接服务端 (ip , 端口）
        //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);//从本地连接，因此第一个参数传入本机地址（也可以写127.0.0.1）
        System.out.println("客户端 socket返回=" + socket.getClass());
        
        //2. 连接上后，生成Socket, 通过socket.getOutputStream()
        //   得到 和 socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();//OutputStream这个是个抽象类，之所以没有报错是因为这里SocketOutputStream类向上转型
        //3. 通过输出流，写入数据到 数据通道
        outputStream.write("hello, server".getBytes());
        //设置结束标记：如果不执行这个方法，会导致服务端收消息时阻塞，客户端没有消息他不会断开连接，会一直等待，只能手动断开
        socket.shutdownOutput();
        
        //4.获取和socket关联的输入流. 读取数据(字节)，并显示
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, readLen));
        }        
        
        
        //5. 关闭流对象和socket, 否则造成资源浪费
        inputStream.close();
        outputStream.close();
        socket.close();
        System.out.println("客户端退出.....");
    }
}
```

字符流传输

```java

/*
案例3（使用字符流）
1.编写一个服务器端，和一个客户端
2.服务器端在9999端口监听
3.客户端连接到服务器端，发送"hello，server"，接收服务器的信息后然后退出
4.服务器端先接收到客户端发送的信息，输出后，向客户端发送"hello, client"并退出

注意:1.socket 只能获得字节流，因此传输数据涉及到装箱和拆箱
2.此处使用结束标记 Writer.newLine() 因此对方也只能使用 Reader.readLine() 方法读取
3.在关闭字符包装流时，必须先关输出的，再关输入的，否则报错！！！！
4.socket和文件不一样，从文件中读，读到末尾就到达流的结尾了，所以会返回-1或null，循环结束，但是socket是连接两个主机的桥梁，一端无法知道另一端到底还有没有数据要传输
 */
class SocketTCP03Server {
    public static void main(String[] args) throws IOException {
        //1. 在本机 的9999端口监听, 等待连接
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端，在9999端口监听，等待连接..");
        Socket socket = serverSocket.accept();
        System.out.println("服务端 socket =" + socket.getClass());
        //2. 通过 getInputStream 方法读取客户端写入的数据
        InputStream inputStream = socket.getInputStream();

        //3. IO读取, 使用字符流, 使用 InputStreamReader（转换流）将 inputStream（字节流） 转成字符流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //如果需要读取多行，加入循环语句 while (readline！=null)。但是执行这个方法需要额外在另一头写完数据后调用下socket.shutdownoutput
        String receivedContents = "";
        while ((receivedContents = bufferedReader.readLine()) != null)
            System.out.println(receivedContents);//输出


        //获取socket相关联的输出流
        OutputStream outputStream = socket.getOutputStream();
        //    使用字符输出流的方式回复信息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello client 字符流");
        bufferedWriter.newLine();// 插入一个换行符，表示回复内容的结束
        bufferedWriter.flush();//注意需要手动的flush


        //6.关闭流和socket
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();//关闭

    }
}
class SocketTCP03Client {
    public static void main(String[] args) throws IOException {
        //思路
        //1. 连接服务端 (ip , 端口）
        //解读: 连接本机的 9999端口, 如果连接成功，返回Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("客户端 socket返回=" + socket.getClass());
        //2. 连接上后，生成Socket, 通过socket.getOutputStream()
        //   得到 和 socket对象关联的字节输出流对象
        OutputStream outputStream = socket.getOutputStream();
        //3. 通过字符输出流，写入数据到 数据通道（利用转换流进行包装）
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello, server 字符流\n你好上海");
        bufferedWriter.newLine();//插入一个换行符，表示写入的内容结束, 注意，要求对方使用readLine()!!!!
        //这里是因为因为readline()只读取一行，看见换行符就默认停止读取。
        //将写好的内容传送到目标位置：使用缓冲流后直接shutdown会报错，需要shutdown之前手动刷新
        bufferedWriter.flush();// 如果使用字符流，需要手动刷新，否则数据不会写入数据通道
        socket.shutdownOutput();//如果需要对方用循环语句接收，那么此处需要shutdown

        //4. 获取和socket关联的输入流. 读取数据(字符)，并显示
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);

        //5. 关闭流对象和socket, 必须关闭
        bufferedReader.close();//关闭外层流
        bufferedWriter.close();
        socket.close();
        System.out.println("客户端退出.....");
    }
}

```



注意：

* 端口监听只能监听一次
* 客户端和服务端通常情况下在不同主机



