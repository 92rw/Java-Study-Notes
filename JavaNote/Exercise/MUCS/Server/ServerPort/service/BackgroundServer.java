package Exercise.MUCS.Server.ServerPort.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;
import Exercise.MUCS.Common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class BackgroundServer {
    private ServerSocket ss;
    //将可以登录的用户存放在 HashMap 集合中（当前集合仅涉及到读取，不涉及删改）
    //推荐使用 ConcurrentHashMap 集合（在多线程下安全，可以处理并发）
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();



    static {//在静态代码块，初始化用户信息
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
    }
    //和离线消息相关的代码
    private static ConcurrentHashMap<String, ArrayList<Message>> offlineMsg = new ConcurrentHashMap<>();
    public static ArrayList<Message> getOfflineMsg(String userId){
        if (offlineMsg.containsKey(userId)) {
            ArrayList<Message> listMsg = offlineMsg.get(userId);
            offlineMsg.remove(userId);
            return listMsg;
        }
        return null;
    }

    public static void setOfflineMsg(String receiverId, Message failedMsg){
        if (offlineMsg.containsKey(receiverId)) {
            offlineMsg.get(receiverId).add(failedMsg);
        }
        ArrayList<Message> listOfflineMsg= new ArrayList<>();
        listOfflineMsg.add(failedMsg);
        offlineMsg.put(receiverId, listOfflineMsg);
    }

    public static boolean isUser(String inputId){
        if (validUsers.containsKey(inputId)) return true;
        return false;
    }
    //验证用户是否有效：过关斩将的方法(先写正确的后取反)
    private boolean checkUser(String enteredId, String enteredPwd) {
        User user = validUsers.get(enteredId);
        //找不到用户名
        if (user == null) {
            return false;
        }
        //密码不正确
        if (!user.getUserPwd().equals(enteredPwd)) {
            return false;
        }
        return true;
    }

    public BackgroundServer() {
        //注意：端口可以写在配置文件
        int port = 9999;

        try {
            System.out.println(String.format("服务器在 %d 端口监听",port));
            ss = new ServerSocket(port);
            //启动推送新闻的线程
            new Thread(new ServerMessage()).start();

            while (true) {//当和某个客户端建立连接后，会继续监听
                Socket socket = ss.accept();//如果没有客户端连接，会阻塞在这里

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //得到客户端输入的User对象
                User u = (User) ois.readObject();

                //向客户端返回的数据
                Message message = new Message();

                //验证用户名和密码是否存在：理论上应当传入DB数据库进行验证，此处仅做简单验证
                if(checkUser(u.getUserId(), u.getUserPwd()) &&
                    (ServerThreadCollection.getServerThread(u.getUserId())) == null //利用线程不能重复的特点，实现单点登录
                ) {
                    System.out.println("用户id= " + u.getUserId() + " pwd=" + u.getUserPwd() + " 登录成功");
                    message.setMsgType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    //创建一个线程，持有 socket 对象以便和客户端保持通信
                    ServerConnectClientThread thread = new ServerConnectClientThread(u.getUserId(), socket);
                    thread.start();


                } else {
                    System.out.println("用户id= " + u.getUserId() + " pwd=" + u.getUserPwd() + " 登录验证失败");
                    message.setMsgType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(message);
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果服务器退出了while循环，说明监听结束，可以将 ServerSocket关闭
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
