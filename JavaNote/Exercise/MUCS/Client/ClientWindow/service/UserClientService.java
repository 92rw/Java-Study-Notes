package Exercise.MUCS.Client.ClientWindow.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;
import Exercise.MUCS.Common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    //可能在其他地方使用 User, Socket 信息，因此设置为成员属性
    private User u = new User();
    private Socket socket;

    //根据用户名和密码到服务器验证是否合法
    public boolean checkUser(String userId, String userPwd) {
        boolean b = false;
        //创建 User 对象
        u.setUserId(userId);
        u.setUserPwd(userPwd);

        //将创建的对象发送到服务器
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            //得到对象输出流，发送给服务器
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);
            //这里不能关流，关闭的话socket也会关闭

            //读取服务器回复的对象，向下转型以便读取信息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) ois.readObject();

            if (msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){

                //创建一个和服务器端保持通信的线程，然后把线程传入集合中共同管理
                // -> 将线程封装到 ClientConnectServerThread 类
                ClientConnectServerThread thread = new ClientConnectServerThread(socket);
                thread.start();

                //为了客户端后续扩展（同时和服务器完成多项交互），将线程加入到集合中进行管理
                ClientThreadCollection.addClientThread(userId, thread);
                b = true;
            } else {
                //登录失败，不启动和服务器通信的线程，连接通道关闭
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    //向服务器端请求在线用户列表
    public void getOnlineUser() {
        //建立发送请求的对象
        Message message = new Message();
        message.setSender(u.getUserId());
        message.setMsgType(MessageType.MESSAGE_GET_ONELINERS);

        //得到对象输出流，将数据发送到服务器端
        try {
            //通过管理线程的集合，获取对应的线程，再通过此线程获取对应的输出流
            /*
            此处原本的代码为
            ObjectOutputStream oos = new ObjectOutputStream(ClientThreadCollection.getClientThread(u.getUserId()).getSocket().getOutputStream());
             因为当前只有一个线程，所以socket唯一。这里输出流的写法也可以是
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             */
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //编写方法，退出客户端，并向服务端发送退出系统的 Message 对象
    public void logout() {
        Message message = new Message();
        message.setSender(u.getUserId());
        message.setMsgType(MessageType.MESSAGE_CLIENT_EXIT);

        try {
            //因为当前只有一个线程，所以socket唯一。这里输出流的写法和上面从集合中获取的的写法等价
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + " 退出系统");
            //结束进程
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
