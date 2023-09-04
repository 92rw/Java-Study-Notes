# UDP网络通信编程

UDP协议：用户数据协议User Datagram Protocol

1. 将数据、源、目的封装成数据包，不需要建立连接
2. 每个数据报的大小限制在64K内，不适合传输大量数据
3. 因无需连接，故是不可靠的
4. 发送数据结束时无需释放资源（因为不是面向连接的），速度快
5. 每个数据报都给出了完整的地址信息，包含了发送端的IP地址和端口号，以及接收端的P地址和端口号



## 基本流程

1. 核心的两个类/对象DatagramSocket与DatagramPacket
2. 建立发送端，接收端（没有服务端和客户端概念）
3. 发送数据前，建立数据包/报DatagramPacket对象
4. 调用DatagramSocket的发送、接收方法
5. 关闭DatagramSocket

和TCP协议的区别：TCP是流传输，需要考虑发送结束指令；UDP是数据包传输，数据包不需要结束指令。

因为UDP协议不是面向连接的，所以它不会像TCP一样创建连接。

因此使用UDP通信时需要把数据和目的地设置到Datagram 中，而不是像TCP一样在创建Socket的时候指定目的



在Java中使用UDP编程，仍然需要使用Socket，因为应用程序在使用UDP时必须指定网络接口（IP）和端口号。注意：UDP端口和TCP端口虽然都使用0~65535，但他们是两套独立的端口，即一个应用程序用TCP占用了端口1234，不影响另一个应用程序用UDP占用端口1234。



```java
/*
1.编写一个接收端A，和一个发送端B
2.接收端A在9999端口等待接收数据(receive）
3.发送端B向接收端A发送数据"hello，明天吃火锅~'
4.接收端A接收到发送端B发送的数据，回复“好的，明天见"，再退出
5.发送端接收回复的数据，再退出
 */
class UDPReceiverA {
    public static void main(String[] args) throws IOException {
        //1. 创建一个 DatagramSocket 对象，准备在9999接收数据
        DatagramSocket socket = new DatagramSocket(9999);
        //2. 构建一个 DatagramPacket 对象，准备接收数据
        //   一个 UDP 数据包最大 64k，因此该数组最大长度是64 * 1024
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        //3. 调用 接收方法, 将通过网络传输的 DatagramPacket 对象填充到 packet对象
        //注意: 当有数据包发送到 本机的9999端口时，就会接收到数据
        //  如果没有数据包发送到 本机的9999端口, 就会阻塞等待.
        System.out.println("接收端A 等待接收数据..");
        socket.receive(packet);

        //4. 可以把packet 进行拆包，取出数据，并显示.
        int start = packet.getOffset();//得到数据在缓冲区的起始位置
        int length = packet.getLength();//实际接收到的数据字节长度
        byte[] data = packet.getData();//接收到数据
        //getRemoteSocketAddress()方法得到null，对面的ip地址需要从数据包中获取
        System.out.println("收到" + packet.getSocketAddress() +"发来的消息");
        String s = new String(data, start, length, StandardCharsets.UTF_8);
        System.out.println(s);


        //===回复信息给B端
        //将需要发送的数据，封装到 DatagramPacket对象
        data = "好的, 明天见".getBytes(StandardCharsets.UTF_8);
        //原路返回
        packet.setData(data);

        socket.send(packet);//发送

        //5. 关闭资源
        socket.close();
        System.out.println("A端退出...");

    }
}

class UDPSenderB {
    public static void main(String[] args) throws IOException {

        //1.创建 DatagramSocket 对象
        DatagramSocket socket = new DatagramSocket();
        //设置接收UDP包时，等待时间最多不会超过1秒，否则在没有收到UDP包时，客户端会无限等待下去
        socket.setSoTimeout(1000);
        //在DatagramSocket实例中保存服务器端的IP和端口号，确保该实例不向其他地址发送
        socket.connect(InetAddress.getByName("127.0.0.1"),9999);

        //2. 将需要发送的数据，封装到 DatagramPacket对象
        byte[] data = "hello 明天吃火锅~".getBytes(); //

        //说明: 还有一种构造器，除了data和data.length，另外写入 主机(IP) , 端口
        DatagramPacket packet = new DatagramPacket(data, data.length);

        socket.send(packet);

        //3.=== 接收从A端回复的信息
        //(1)   构建一个 DatagramPacket 对象，准备接收数据
        //   在前面讲解UDP 协议时，老师说过一个数据包最大 64k
        byte[] buf = new byte[1024];
        packet = new DatagramPacket(buf, buf.length);
        //(2)    调用 接收方法, 将通过网络传输的 DatagramPacket 对象
        //   填充到 packet对象
        //老师提示: 当有数据包发送到 本机的9998端口时，就会接收到数据
        //   如果没有数据包发送到 本机的9998端口, 就会阻塞等待.
        socket.receive(packet);

        //(3)  可以把packet 进行拆包，取出数据，并显示.
        data = packet.getData();//接收到数据
        String s = new String(data, packet.getOffset(), packet.getLength());
        System.out.println(s);

        socket.disconnect();//这个方法不是真正地断开连接，而是清除DatagramSocket中的记录
        
        //关闭资源
        socket.close();
        System.out.println("B端退出");
    }
}

```

当服务器收到一个DatagramPacket后，通常必须立刻回复一个或多个UDP包，因为客户端地址在DatagramPacket中，每次收到的DatagramPacket可能是不同的客户端，如果不回复，客户端就收不到任何UDP包。

## UDP编程和TCP编程对比

简单的对话系统，通过TCP方式实现

```java
/*练习题1
（1）使用字符流的方式，编写一个客户端程序和服务器端程序
（2）客户端发送"name"，服务器端接收到后，返回"我是nova"
（3）客户端发送“hobby"，服务器端接收到后，返回"编写java程序"
（4）不是这两个问题，回复“你说啥呢"
 */
class SocketExercise01Server{
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10492);
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String s = br.readLine();
        String answer = null;

        switch (s) {
            case "name":
                answer = "我是 nova";
                break;
            case "hobby":
                answer = "编写java程序";
            default:
                answer = "你说啥呢";
        }

        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(answer);
        bw.newLine();
        bw.flush();

        bw.close();
        br.close();
        socket.close();
        serverSocket.close();//关闭
    }
}

class SocketExercise01Client{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 10492);

        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入问题");
        String question = scanner.next();
        bw.write(question);
        bw.newLine();
        bw.flush();

        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String s = br.readLine();
        System.out.println(s);

        br.close();
        bw.close();
        socket.close();
    }
}
```

简单的对话系统，通过UDP方式实现

```java
/*
练习题2
（1）编写一个接收端A，和一个发送端B，使用UDP协议完成
（2）接收端在10086端口等待接收数据（receive）
（3）发送端向接收端发送数据“四大名著是哪些"
（4）接收端接收到发送端发送的问题后，返回"你懂得"，否则返回what?
（5）接收端收到886，结束监听并退出
 */
class SocketExercise02Receiver{
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(10086);
        byte[] bytes = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        do{
            socket.receive(packet);

            int length = packet.getLength();
            byte[] data = packet.getData();
            String s = new String(data,0,length);

            String answer = "what?";
            switch (s){
                case "886":
                    break;
                case "四大名著":
                    answer = "你懂得";
                default:
                    data = answer.getBytes();
                    packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(),10085);
                    socket.send(packet);
            }
        }while(true);
        //socket.close();//JVM自动垃圾回收
    }
}

class SocketExercise02Sender{
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(10085);
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("请输入问题");
            byte[] data = scanner.next().getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), 10086);
            socket.send(packet);

            byte[] receive = new byte[64 * 1024];
            packet = new DatagramPacket(receive, receive.length);
            socket.receive(packet);

            int length = packet.getLength();
            data = packet.getData();
            String s = new String(data,0,length);
            System.out.println(s);

        } while(true);
        //socket.close();
    }
}
```

