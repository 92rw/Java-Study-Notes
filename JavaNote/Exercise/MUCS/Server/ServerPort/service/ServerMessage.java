package Exercise.MUCS.Server.ServerPort.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;
import Exercise.MUCS.Server.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Scanner;

public class ServerMessage implements Runnable{
    private Scanner scanner = new Scanner(System.in);
    @Override
    public void run() {
        while (true) {
            //使用循环以便发送多条消息
            System.out.println("请输入服务器推送的消息[输入exit退出服务线程]");
            String announcement = Utility.readString(100);
            if (announcement.equals("exit")) break;
            //构建消息对象
            Message msg = new Message();
            msg.setSender("服务器管理员");
            msg.setContent("(系统消息) " + announcement);
            msg.setSendTime(java.time.LocalDateTime.now().toString());
            System.out.println("服务器推送公共消息：" + announcement);
            msg.setMsgType(MessageType.MESSAGE_PUBLIC_MESSAGE);

            //指定接收方
            Collection<ServerConnectClientThread> onlineThreads = ServerThreadCollection.publicMsgReceivers();
            for (ServerConnectClientThread thread : onlineThreads) {
                try {
                    ObjectOutputStream receiverOOS = new ObjectOutputStream(thread.getSocket().getOutputStream());
                    receiverOOS.writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
