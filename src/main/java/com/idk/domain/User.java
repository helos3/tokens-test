package com.idk.domain;

import io.ebean.Model;
import io.ebean.annotation.SoftDelete;
import org.jetbrains.annotations.NotNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by rushan on 4/25/2017.
 */
public class User extends Model {

    @Id
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String salt;
    @SoftDelete
    private boolean deleted;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Token> tokens;

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

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
