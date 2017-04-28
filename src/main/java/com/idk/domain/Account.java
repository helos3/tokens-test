package com.idk.domain;

import io.ebean.Ebean;
import io.ebean.Model;
import io.ebean.annotation.SoftDelete;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
public class Account extends Model {

    public static Account find(String login) {
        return Ebean.find(Account.class, login);
    }

    @Id
    @NotNull
    private String login;
    @NotNull
    private String password;

    private String salt;
    @SoftDelete
    private boolean deleted;

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
