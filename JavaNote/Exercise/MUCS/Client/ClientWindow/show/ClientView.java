package Exercise.MUCS.Client.ClientWindow.show;

import Exercise.MUCS.Client.ClientWindow.service.UserClientService;
import Exercise.MUCS.Client.ClientWindow.service.UserFileService;
import Exercise.MUCS.Client.ClientWindow.service.UserMessageService;
import Exercise.MUCS.Client.ClientWindow.utils.Utility;

public class ClientView {
    private boolean loop = true;//控制是否显示菜单
    private String key;
    UserClientService userClientService = new UserClientService();
    UserMessageService userMessageService= new UserMessageService();
    UserFileService userFileService = new UserFileService();

    public static void main(String[] args) {
        new ClientView().mainMenu();
        System.out.println("客户端退出");
    }

    //显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("========欢迎使用多用户通信系统========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);
            //根据用户输入，处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户名：");
                    String userId = Utility.readString(50);//最大输入数值50个
                    System.out.print("请输入密码：");
                    String userPwd = Utility.readString(50);//最大输入数值50个

                    if (userClientService.checkUser(userId, userPwd)) {
                        //给二级界面单独写一个方法的话，想用UserID这个属性就比较麻烦
                        System.out.println("用户 " + userId + " 登录成功");
                        while (loop) {
                            System.out.println("========多用户通信系统二级菜单(用户 " + userId + " )========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.getOnlineUser();
                                    break;
                                case "2":
                                    System.out.print("请输入群发消息内容：");
                                    String publicMsg = Utility.readString(50);//最大输入数值50个
                                    userMessageService.sendPublicMessage(publicMsg, userId);
                                    break;
                                case "3":
                                    System.out.print("请输入私聊对象的用户名：");
                                    String receiverId = Utility.readString(50);//最大输入数值50个
                                    //如果要判断发送方和接收方是否相同，可以在此处执行判断或在服务器端 msg.getSender().equals(msg.getReceiver())
//                                    if (userId.equals(receiverId)) {
//                                        System.out.println("不可向自己发送私聊信息");
//                                    } else {
                                    System.out.print("请输入聊天内容：");
                                    String privateMsg = Utility.readString(50);
                                    userMessageService.sendPrivateMessage(receiverId, privateMsg, userId);
                                    break;
                                case "4":
                                    System.out.print("请输入文件接收方的用户名：");
                                    String fileReceiver = Utility.readString(50);
                                    System.out.print("请输入文件本地目录(形式 d:\\\\xx.jpg)：");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入文件需要存储在对方目录下的位置(形式 d:\\\\yy.jpg)：");
                                    String dest = Utility.readString(100);
                                    userFileService.sendFile(src, dest, userId, fileReceiver);
                                    break;
                                case "9":
                                    loop = false;
                                    userClientService.logout();
                                    break;
                            }
                        }
                    } else {//服务器登录失败
                        System.out.println("用户 " + userId + " 登录失败");
                        //这里可以不写break，程序会执行到下面的break
                    }
                    break;

                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
