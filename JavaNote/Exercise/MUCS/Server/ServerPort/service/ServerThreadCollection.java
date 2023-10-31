package Exercise.MUCS.Server.ServerPort.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ServerThreadCollection {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    //添加线程对象
    public static void addServerThread(String userId, ServerConnectClientThread thread){
        hm.put(userId, thread);
    }

    //根据用户名获取线程
    public static ServerConnectClientThread getServerThread(String userId) {
        return hm.get(userId);
    }

    //删除线程对象
    public static void removeThread(String userId) {
        hm.remove(userId);
    }

    //返回在线用户列表
    public static String getOnlineUsers() {
        //遍历集合
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        //此处建议使用StringBuffer，因为这个是多线程情况
        while (iterator.hasNext()) {
            //遍历 keySet 得到的对象是 String 类型，因此可以直接拼接
            onlineUserList += iterator.next() + " ";
        }
        return onlineUserList;
    }
    //判断某个用户是否在线
    public static boolean isOnline(String userId) {
        return hm.containsKey(userId);
    }

    //返回集合中的值，用于群发消息
    public static Collection<ServerConnectClientThread> publicMsgReceivers() {
        return hm.values();
    }
}
