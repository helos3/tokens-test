package com.idk.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.idk.secure.StringEncoder;
import io.ebean.Ebean;
import io.ebean.Model;
import io.ebean.annotation.JsonIgnore;
import io.ebean.annotation.SoftDelete;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="user_") //reserved word in postgres
public class User extends Model {

    public static User find(String login) {
        return Ebean.find(User.class, login);
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
