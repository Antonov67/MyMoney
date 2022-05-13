package com.example.mymoney;

public class User {
    private String login;
    private String pswrd;
    private String name;


    public User(String login, String pswrd, String name) {
        this.login = login;
        this.pswrd = pswrd;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getPswrd() {
        return pswrd;
    }

    public String getName() {
        return name;
    }
}
