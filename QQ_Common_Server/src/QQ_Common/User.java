package QQ_Common;

import java.io.Serializable;

public class User implements Serializable {
    private static long serialVersionUID=1L;
    /*
    用户名长度不超过20
    密码长度不超过15
     */
    private String userId;
    private String passWord;

    public User(String userId, String passWord) {
        this.userId = userId;
        this.passWord = passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
