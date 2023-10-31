package Exercise.MUCS.Common;

public interface MessageType {
    //解读：
    // 1.接口里写成员变量默认被 public static final 修饰
    // 2.不同常量的值，表示不同的消息类型
    String MESSAGE_LOGIN_SUCCEED = "1"; //用户登录成功
    String MESSAGE_LOGIN_FAILED = "2";  //用户登录失败
    String MESSAGE_NORMAL_MESSAGE = "3";//普通消息
    String MESSAGE_PUBLIC_MESSAGE = "4";//群发消息
    String MESSAGE_GET_ONELINERS = "5"; //获取在线用户
    String MESSAGE_RET_ONELINERS = "6"; //返回在线用户
    String MESSAGE_FILE_TRANSFER = "7"; //文件传输
    String MESSAGE_CLIENT_EXIT = "9";   //客户端请求退出
}
