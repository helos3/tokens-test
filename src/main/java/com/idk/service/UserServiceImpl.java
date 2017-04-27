package com.idk.service;

import com.idk.secure.*;
import com.idk.utils.Credentials;
import com.idk.domain.Token;
import com.idk.domain.User;
import com.idk.utils.Pair;
import com.idk.utils.Status;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private SaltGenerator saltGenerator;
    private IStringEncoder encoder;

    @Inject
    public UserServiceImpl(SaltGenerator saltGenerator, IStringEncoder encoder) {
        this.saltGenerator = saltGenerator;
        this.encoder = encoder;
    }

    @Override
    public Pair<Status, Token> register(Credentials credentials) {
        User user = User.find(credentials.getLogin());
        if (user != null)
            return Pair.of(Status.of(HttpServletResponse.SC_BAD_REQUEST, "User already exists"), null);

        byte[] salt = saltGenerator.generate();
        user = new User();
        user.setLogin(credentials.getLogin());
        String passwordHashed = encoder.encodeWithSalt(credentials.getPassword().getBytes(), salt);
        user.setPassword(passwordHashed);
        user.setSalt(encoder.encodeBase64(salt));
        user.setDeleted(false);
        user.save();
        return Pair.of(Status.success(), createTokenAndSave(user));
    }

    @Override
    public Pair<Status, Token> authenticate(Credentials credentials) {
        User user = User.find(credentials.getLogin());
        if (user == null ||
            !encoder.encodeWithSalt(
                credentials.getPassword().getBytes(),
                encoder.decodeBase64(user.getSalt()))
                .equals(user.getPassword()))
            return Pair.of(Status.of(HttpServletResponse.SC_BAD_REQUEST, "Wrong user credentials"), null);


        List<Token> tokens = Token.findByLogin(user.getLogin());
        Optional<Token> found = tokens.stream().filter(token -> !token.isExpired()).findFirst();

        return Pair.of(Status.success(), found.orElse(createTokenAndSave(user)));

    }

    @Override
    public Pair<Status, String> authenticate(String token) {
        Token found = Token.find(token);
        if (Token.find(token) == null || found.isExpired())
            return Pair.of(Status.of(HttpServletResponse.SC_NOT_FOUND, "Not Found"), null);

        return Pair.of(Status.success(), found.getUser().getLogin());
    }

    private Token createTokenAndSave(User user) {
        LocalDateTime creation = LocalDateTime.now();
        Long hashes = (long)creation.hashCode() << 32
            ^ (long) user.getSalt().hashCode() << 16
            ^ user.getLogin().hashCode();
        String tokenString = encoder.encodeWithoutSalt(ByteBuffer
            .allocate(16)
            .putLong(hashes)
            .array());

        System.out.println(tokenString.length());
        Token token = new Token();
        token.setUser(user);
        token.setExpired(false);
        token.setCreationDate(creation);
        token.setToken(tokenString);
        token.save();
        return token;
    }
}
