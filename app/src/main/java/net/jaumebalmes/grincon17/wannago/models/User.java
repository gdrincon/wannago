package net.jaumebalmes.grincon17.wannago.models;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String userName;
    private String pwd;
    private String mail;

    public User() {
    }

    public User(String userName, String pwd, String mail) {
        this.userName = userName;
        this.pwd = pwd;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
