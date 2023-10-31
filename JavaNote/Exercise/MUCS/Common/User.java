package Exercise.MUCS.Common;

import java.io.Serializable;

public class User implements Serializable/*实现网络传输*/ {
    private static final long serialVersionUID = 1L;//增强兼容性，不然运行时抛异常
    private String userId;  //用户名
    private String userPwd; //密码


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public User(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

    public User() {}//无参构造器
}
