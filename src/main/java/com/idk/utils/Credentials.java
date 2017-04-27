package com.idk.utils;

public class Credentials {

    private String login, password;

    public Credentials() {

    }

    public boolean areCorrect() {
        return login != null && login.length() > 3
            && password != null && password.length() > 5;
    }

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
