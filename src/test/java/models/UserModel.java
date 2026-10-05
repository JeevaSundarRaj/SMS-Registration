package models;

public class UserModel {
    public final String email;
    public final String password;
    public final String loginType;

    public UserModel(String email, String password, String loginType) {
        this.email = email;
        this.password = password;
        this.loginType = loginType;
    }
}
