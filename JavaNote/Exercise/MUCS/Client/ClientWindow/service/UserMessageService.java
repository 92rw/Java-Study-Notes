package Exercise.MUCS.Client.ClientWindow.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserMessageService{

    /**
     *
     * @param receiverId 消息接收方
     * @param msgContents 消息内容
     * @param senderId 消息发送方
     */
    public void sendPrivateMessage(String receiverId, String msgContents, String senderId) {
        //构建 Message 对象
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_NORMAL_MESSAGE);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        message.setContent(msgContents);
        message.setSendTime(java.time.LocalDateTime.now().toString());//也可以使用第一代时间日期new java.util.Date().toString()
        System.out.println(String.format("你 对 %s 说 %s", receiverId, msgContents));

        //将消息发送给服务器端
        try {
            //此处原本的代码为
            ObjectOutputStream oos = new ObjectOutputStream(ClientThreadCollection.getClientThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param msgContents 消息内容
     * @param senderId    发送者
     */
    public void sendPublicMessage(String msgContents, String senderId){
        //构建 Message 对象
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_PUBLIC_MESSAGE);
        message.setSender(senderId);
        message.setContent(msgContents);
        String sendtime = java.time.LocalDateTime.now().toString();
        message.setSendTime(sendtime);
        System.out.println(String.format("你 在 %s 发布群发消息： %s", sendtime, msgContents));

        //将消息发送给服务器端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ClientThreadCollection.getClientThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
