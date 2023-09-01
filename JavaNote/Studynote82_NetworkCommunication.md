
# 网络通信
1. 概念：两台设备之间通过网络实现数据传输
2. 网络通信：将数据通过网络从一台设备传输到另一台设备
3. java.net包下提供了一系列的类或接口，供程序员使用，完成网络通信

## 网络
1. 概念：两台或多台设备通过一定物理设备连接起来构成了网络
2. 根据网络的覆盖范围不同，对网络进行分类：
   * 局域网：覆盖范围最小，仅仅覆盖一个教室或一个机房
   * 城域网：覆盖范围较大，可以覆盖个城市
   * 广域网：覆盖范围最大，可以覆盖全国，甚至全球，万维网是广域网的代表

## ip地址
1. 概念：用于唯一标识网络中的每台计算机/主机

2. ```powershell
   ipconfig #命令行查看ip地址
   ```

3. ip地址的组成=网络地址+主机地址

4. IPV4以点分十进制表示，比如：192.168.16.69，每一个十进制数的范围：0~255，实际上是一个4字节（32位）的整数，转换为4个十六进制数值 C0 A8 10 45，而0xc0a81045表示数字3232239685

5. IPV6以16个字节（128位）表示，例如2001:0DA8:100A:0000:0000:1020:F2F3:1428

6. IP地址通过子网掩码过滤后得到网络号，网络号相同的计算机在同一网络内，可以直接通信；网络号不同的设备需要通过网关（路由器或交换机）通信

7. 计算机的网卡需要配置IP地址、子网掩码、网关IP地址

8. 相关概念可参考文章：[简明socket编程 | 蝉沐风 (chanmufeng.com)](https://www.chanmufeng.com/posts/network-programming/network-programming.html#_4-1-ipv4与ipv6)

### ipv4地址分类

| 类型 |      二进制编号格式      |       转换为十进制范围       |            备注             |
| :--: | :----------------------: | :--------------------------: | :-------------------------: |
| A类  |  0+7位网络号+24位主机号  |  0.0.0.0 到 127.255.255.255  | 特殊的127.0.0.1表示主机地址 |
| B类  | 10+14位网络号+16位主机号 | 128.0.0.0 到 191.255.255.255 |                             |
| C类  | 110+21位网络号+8位主机号 | 192.0.0.0 到 223.255.255.255 |                             |
| D类  |    1110+28位多播组号     | 224.0.0.0 到 239.255.255.255 |                             |
| E类  |  11110+27位（留待后用）  | 240.0.0.0 到 247.255.255.255 |                             |

* 常规宽带网络运营商一般分配的都是临时B类地址，除非你租用的固定IP的才给固定B类地址
* 一般局域网内部终端都是C类地址
* 网络号和主机号的长度不是按分类固定的，网络号占位越短，可容纳的主机越多
* 主机号为 0 的地址是广播地址

### InetAddress 类

相关方法

| 方法              | 作用                                      |
| ----------------- | ----------------------------------------- |
| getLocalHost()    | 获取本机的InetAddress 对象                |
| getByName(String) | 根据 指定主机名/域名 获取 InetAddress对象 |
| getHostAddress()  | 获取对应 InetAddress 对象的IP地址         |
| getHostName()：   | 获取对应 InetAddress 对象的主机名/域名    |

案例演示

```java
import java.net.InetAddress;
import java.net.UnknownHostException;

class InetAddressMethod {//演示InetAddress 类的使用
    public static void main(String[] args) throws UnknownHostException {
        //1. getLocalHost()：获取本机的InetAddress 对象
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);//主机名/IP地址

        //2. getByName(String)：根据 指定主机名 获取 InetAddress对象
        InetAddress host1 = InetAddress.getByName("DESKTOP-S4MP84S");//这个字符串是设备名称
        System.out.println("host1=" + host1);//DESKTOP-S4MP84S/192.168.12.1

        //getByName(String)还可以根据 域名 返回 InetAddress对象,
        InetAddress host2 = InetAddress.getByName("www.baidu.com");
        System.out.println("host2=" + host2);//www.baidu.com / 110.242.68.4

        //3. getHostAddress()：获取对应 InetAddress 对象的IP地址
        String hostAddress = host2.getHostAddress();//获取到的是InetAddress对象的IP地址
        System.out.println("host2 对应的ip = " + hostAddress);//110.242.68.4

        //4. getHostName()：获取对应 InetAddress 对象的主机名/域名
        String hostName = host2.getHostName();
        System.out.println("host2对应的主机名/域名=" + hostName); // www.baidu.com
    }
}
```



## 域名
1. https://github.com/
2. 好处：为了方便记忆，解决记ip的困难
3. 概念：将ip地址映射成域名

## 端口号
1. 概念：用于标识计算机上某个特定的网络程序
2. 表示形式：以整数形式，范围0~65535（2个字节，对应16位）
3. 小于1024的端口属于*特权端口*，需要管理员权限，大于1024的端口可以由任意用户的应用程序打开。

| 系统程序 | 端口号 | 常用网络程序 | 端口号 |
| -------- | ------ | ------------ | ------ |
| ftp      | 21     | tomcat       | 8080   |
| ssh      | 22     | MySQL        | 3306   |
| smtp     | 25     | Oracle       | 1521   |
| http     | 80     | SQLserver    | 1433   |





## 网络模型

OSI（Open System Interconnect）网络模型是ISO组织定义的一个计算机互联的标准模型，注意它只是一个定义，目的是为了简化网络各层的操作，提供标准接口便于实现和维护。这个模型从上到下依次是：

- 应用层，提供应用程序之间的通信；
- 表示层：处理数据格式，加解密等等；
- 会话层：负责建立和维护会话；
- 传输层：负责提供端到端的可靠传输；
- 网络层：负责根据目标地址选择路由来传输数据；
- 链路层和物理层负责把数据进行分片并且真正通过物理网络传输，例如，无线网、光纤等。

| 数据                         | TCP/IP模型      | 对应协议               |
| ---------------------------- | --------------- | ---------------------- |
| 用户数据                     |                 |                        |
| Appl首部+用户数据            | 应用层          | HTTP、ftp、telnet、DNS |
| TCP首部+应用数据=TCP段       | 传输层（TCP）   | TCP、UDP               |
| IP首部+TCP段=IP数据报        | 网络层（IP）    | IP、ICMP、ARP          |
| 以太网首部+IP数据报=以太网帧 | 物理+数据链路层 | Link                   |

备注：OSI模型有7层

### 网络通信协议

在网络编程中，数据的组织形式就是协议

TCP/IP（Transmission Control Protocol/Internet Protocol)的简写。

中文名为传输控制协议/因特网互联协议，又叫网络通讯协议，这个协议是Internet最基本的协议

Internet国际互联网络的基础，简单地说，就是由网络层的IP协议和传输层的TCP协议组成的。

| 协议名  | 类型         | 说明                                   |
| ------- | ------------ | -------------------------------------- |
| IP协议  | 分组交换     | 只负责发数据包，不保证顺序和正确性     |
| TCP协议 | 传输控制协议 | 面向连接的协议，支持可靠传输和双向通信 |
| UDP协议 | 数据报文协议 | 无连接协议，不保证可靠传输             |







