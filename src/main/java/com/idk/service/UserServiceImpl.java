package com.idk.service;

import com.idk.domain.Token;
import com.idk.domain.User;
import com.idk.secure.SaltGenerator;
import com.idk.secure.StringEncoder;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by rushan on 4/25/2017.
 */
public class UserServiceImpl implements UserService {

    private SaltGenerator saltGenerator;
    private StringEncoder encoder;

    @Inject
    public UserServiceImpl(SaltGenerator saltGenerator, StringEncoder encoder) {
        this.saltGenerator = saltGenerator;
        this.encoder = encoder;
    }

    @Override
    public Optional<Token> register(String login, String password) {
        if (User.find(login) != null)
            return Optional.empty();



        byte[] salt = saltGenerator.generate();
        String hash = encoder.encodeWithSalt(password.getBytes(), salt);
//        return Optional.of(User.create(login, hash, encoder.encodeBase64(salt)));

        return null;

    }


    @Override
    public Optional<Token> authenticate(String login, String password) {
        return null;
    }

    @Override
    public Optional<Token> authenticate(String token) {
        return null;
    }
}
