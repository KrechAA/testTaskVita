package com.krech.vita.domain.rest.request;

public class LogInRequest {
    private String login;

    private String password;

    public LogInRequest() {
    }

    public LogInRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
