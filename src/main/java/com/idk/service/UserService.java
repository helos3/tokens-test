package com.idk.service;

import com.idk.domain.Token;
import com.idk.domain.User;
import spark.Spark;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by rushan on 4/25/2017.
 */
public interface UserService {

    Optional<Token> register(String login, String password);

    Optional<Token> authenticate(String login, String password);

    Optional<Token> authenticate(String token);

}
