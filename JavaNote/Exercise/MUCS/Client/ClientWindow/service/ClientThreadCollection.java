package Exercise.MUCS.Client.ClientWindow.service;

import java.util.HashMap;

public class ClientThreadCollection {
    //将多个线程放入到一个 HashMap 集合中，key 表示用户名
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addClientThread(String userId, ClientConnectServerThread thread){
        hm.put(userId, thread);
    }

    //通过 userID 获取对应的线程
    public static ClientConnectServerThread getClientThread(String userId){
        return hm.get(userId);
    }
}
