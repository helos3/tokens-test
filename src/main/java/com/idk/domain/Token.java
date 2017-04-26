package com.idk.domain;

import io.ebean.Model;
import io.ebean.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by rushan on 4/25/2017.
 */

public class Token extends BaseModel<String> {

    public static List<Token> find(String login) {
        return db().find(Token.class).where().eq("login", login).findList();
    }

    @Id
    private String token;

    @ManyToOne
    @JsonIgnore
    private User user;

    @JsonIgnore
    private Date creationDate;

    @JsonIgnore
    private boolean expired;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
