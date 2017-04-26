package com.idk.service;

import com.idk.domain.Token;
import com.idk.domain.User;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by rushan on 4/25/2017.
 */
public class UserServiceImpl implements UserService {





    @Override
    public Optional<User> createUser(String login, String password) {

        return null;
    }

    @Override
    public Optional<User> createUser(User user) {
        return null;
    }

    @Override
    public Optional<Token> authenticate(String login, String password) {
        return null;
    }

    @Override
    public Optional<Token> authenticate(User user) {
        return null;
    }
}
