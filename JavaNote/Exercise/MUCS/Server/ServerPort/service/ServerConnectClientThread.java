package Exercise.MUCS.Server.ServerPort.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(String userId, Socket socket) {
        this.socket = socket;
        this.userId = userId;
    }
    //本类代码不需要调用此方法，设置这个方法用于其他类调用
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //System.out.println("服务器和客户端 " + userId + " 保持通信，读取数据");

                //读取离线消息
                //判断是否接受离线消息
                ArrayList<Message> listOfflineMsg = BackgroundServer.getOfflineMsg(userId);
                if(listOfflineMsg != null) {
                    for (int i = 0; i < listOfflineMsg.size(); i++) {
                        Message offlineMessage = listOfflineMsg.get(i);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(offlineMessage);
                        listOfflineMsg.remove(i);
                    }
                }

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();
                switch (msg.getMsgType()) {
                    case MessageType.MESSAGE_GET_ONELINERS:
                        System.out.println(msg.getSender() + " 请求获取在线用户列表");
                        String onlineUsers = ServerThreadCollection.getOnlineUsers();
                        //构建返回给客户端的数据
                        Message returnMessage = new Message();
                        returnMessage.setMsgType(MessageType.MESSAGE_RET_ONELINERS);
                        returnMessage.setContent(onlineUsers);
                        returnMessage.setReceiver(msg.getSender());
                        //传入对象输出流
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(returnMessage);
                        break;
                    case MessageType.MESSAGE_CLIENT_EXIT:
                        System.out.println("用户 " + msg.getSender() + " 退出系统");
                        //关闭和客户端的连接，并从集合中删除
                        ServerThreadCollection.removeThread(msg.getSender());
                        socket.close();
                        //退出线程：如果break需要添加标签否则只是退出switch循环，return才能退出外层while循环
                        return;
                    case MessageType.MESSAGE_NORMAL_MESSAGE:
                        if (ServerThreadCollection.isOnline(msg.getReceiver())){
                            System.out.println(String.format("用户 %s 向 %s 发送私聊信息成功", msg.getSender(), msg.getReceiver()));
                            //访问到接收方的线程
                            ServerConnectClientThread receiverThread = ServerThreadCollection.getServerThread(msg.getReceiver());
                            /*此处原本的代码时新建一个对象输出流，需要在这个类中新建getSocket()方法，对应的代码是
                            ObjectOutputStream receiverOOS = new ObjectOutputStream(receiverThread.getSocket().getOutputStream());
                            但因为是在本类中，private属性可以直接调用。.socket和.getSocket()得到的是一个Socket实例
                            ObjectOutputStream receiverOOS = new ObjectOutputStream(receiverThread.socket.getOutputStream());
                             */
                            ObjectOutputStream receiverOOS = new ObjectOutputStream(receiverThread.socket.getOutputStream());
                            receiverOOS.writeObject(msg);
                        } else {

                            String failReason = "用户离线";
                            if (!BackgroundServer.isUser(msg.getReceiver())) failReason = "用户不存在";
                            System.out.println(String.format("用户 %s 向 %s 发送私聊信息失败", msg.getSender(), msg.getReceiver()));
                            msg.setContent("(离线消息) " + msg.getContent());
                            BackgroundServer.setOfflineMsg(msg.getReceiver(), msg);
                            //向发送方返回信息
                            Message failedMessage = new Message();
                            failedMessage.setMsgType(MessageType.MESSAGE_NORMAL_MESSAGE);
                            failedMessage.setSender("服务器端");
                            failedMessage.setSendTime(msg.getSendTime());
                            failedMessage.setContent("消息发送失败，原因：" + msg.getReceiver() + failReason);
                            ObjectOutputStream senderOOS = new ObjectOutputStream(socket.getOutputStream());
                            senderOOS.writeObject(failedMessage);
                        }
                        break;
                    case MessageType.MESSAGE_PUBLIC_MESSAGE:
                        System.out.println("用户 " + msg.getSender() + " 发送了群发消息");
                        Collection<ServerConnectClientThread> onlineThreads = ServerThreadCollection.publicMsgReceivers();
                        for (ServerConnectClientThread thread : onlineThreads) {
                            if (thread != ServerThreadCollection.getServerThread(msg.getSender())){//排除群发消息的用户
                                ObjectOutputStream receiverOOS = new ObjectOutputStream(thread.socket.getOutputStream());
                                receiverOOS.writeObject(msg);
                            }

                        }
                        break;
                    case MessageType.MESSAGE_FILE_TRANSFER:
                        if (!ServerThreadCollection.isOnline(msg.getReceiver())) {
                            System.out.println(String.format("用户 %s 向 %s 发送文件失败", msg.getSender(), msg.getReceiver()));
                            break;
                        }
                        ServerConnectClientThread receiverThread = ServerThreadCollection.getServerThread(msg.getReceiver());
                        ObjectOutputStream receiverOOS = new ObjectOutputStream(receiverThread.socket.getOutputStream());
                        receiverOOS.writeObject(msg);
                        System.out.println(String.format("用户 %s 向 %s 发送文件成功", msg.getSender(), msg.getReceiver()));
                        break;
                    default:
                        System.out.println("获取到其他类型的信息，暂时无法处理");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

        }
    }
}
