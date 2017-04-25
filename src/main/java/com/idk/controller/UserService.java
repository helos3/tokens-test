package com.idk.controller;

import com.idk.domain.Token;
import com.idk.domain.User;
import spark.Spark;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by rushan on 4/25/2017.
 */
public interface UserService {

    Optional<User> createUser(String login, String password);

    Optional<Token> authenticate(String login, String password);
}
