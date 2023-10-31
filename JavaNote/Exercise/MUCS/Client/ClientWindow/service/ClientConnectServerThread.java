package Exercise.MUCS.Client.ClientWindow.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    //该线程持有Socket
    private Socket socket;

    //构造器中接收Socket对象
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //让线程持续运转来让socket一直连接Server
        while (true) {
            try {
                //System.out.println("客户端线程，等待读取服务器端发送的信息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();//如果服务器没有发送信息，该线程会阻塞在此处
                switch (msg.getMsgType()) {
                    case MessageType.MESSAGE_RET_ONELINERS:
                        //服务器端返回在线用户列表
                        String[] onlineUsers = msg.getContent().split(" ");
                        System.out.println("\n======当前在线用户列表======");
                        for (String user : onlineUsers) {
                            System.out.println("用户：" + user);
                        }
                        break;
                    case MessageType.MESSAGE_NORMAL_MESSAGE:
                        //将服务器转发的消息，显示到控制台
                        System.out.println(String.format("\n%s 于 %s 对你发送消息\n消息内容：%s ", msg.getSender(), msg.getSendTime(), msg.getContent()));
                        break;
                    case MessageType.MESSAGE_PUBLIC_MESSAGE:
                        System.out.println(String.format("\n%s 于 %s 发布群发消息\n消息内容： %s ", msg.getSender(), msg.getSendTime(), msg.getContent()));
                        break;
                    case MessageType.MESSAGE_FILE_TRANSFER:
                        System.out.println(String.format("\n%s 对你发送文件，将存放到 %s 目录下", msg.getSender(), msg.getDest()));
                        //此处可添加代码，方便用户另行指定保存位置
                        FileOutputStream fileOutputStream = new FileOutputStream(msg.getDest());
                        fileOutputStream.write(msg.getFileBytes());
                        fileOutputStream.close();
                        System.out.println("\n保存文件成功");
                        break;
                    default:
                        System.out.println("服务器返回未知信息");
                }
                //sleep(50);//控制显示顺序
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
