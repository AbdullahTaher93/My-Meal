package com.apps.my_meal;
//create new user with his info
public class Users {
    String User_ID,User_Nickname,User_email,User_password;

    public Users(String user_ID, String user_Nickname, String user_email, String user_password) {
        User_ID = user_ID;
        User_Nickname = user_Nickname;
        User_email = user_email;
        User_password = user_password;
    }

    public Users() {
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Nickname() {
        return User_Nickname;
    }

    public void setUser_Nickname(String user_Nickname) {
        User_Nickname = user_Nickname;
    }

    public String getUser_email() {
        return User_email;
    }

    public void setUser_email(String user_email) {
        User_email = user_email;
    }

    public String getUser_password() {
        return User_password;
    }

    public void setUser_password(String user_password) {
        User_password = user_password;
    }
}
