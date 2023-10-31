package Exercise.MUCS.Client.ClientWindow.service;

import Exercise.MUCS.Common.Message;
import Exercise.MUCS.Common.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserFileService {
    /**
     *
     * @param src        源文件目录
     * @param dest       文件目标目录
     * @param senderId   发送方
     * @param receiverId 接收方
     */
    public void sendFile(String src,String dest,String senderId, String receiverId){
        //封装 Message 对象
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_FILE_TRANSFER);
        message.setSender(senderId);
        message.setReceiver(receiverId);
        message.setSrc(src);
        message.setDest(dest);

        //读取文件
        FileInputStream fileInputStream = null;
        //用数组的形式，这样转int超过2.1g的文件会缺失大小
        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);
            //将文件对应的字节数组设置到 Message 对象
            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //提示信息
        System.out.println(String.format("\n%s 向 %s 发送文件： %s 到对方电脑的 %s 目录中", senderId, receiverId, src, dest));

        //传送对象到服务器
        try {
            //此处原本的代码为
            ObjectOutputStream oos = new ObjectOutputStream(ClientThreadCollection.getClientThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
