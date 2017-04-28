package com.idk.domain;

import io.ebean.Ebean;
import io.ebean.Model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Token extends Model{

    public static Token find(String token) {
        return Ebean.find(Token.class, token);
    }

    public static List<Token> findByLogin(String login) {
        return db().find(Token.class).where().eq("user_login", login).findList();
    }

    @Id
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_login")
    private Account account;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    private boolean expired;


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
