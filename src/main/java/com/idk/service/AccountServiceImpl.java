package com.idk.service;

import com.idk.domain.Account;
import com.idk.secure.*;
import com.idk.utils.Credentials;
import com.idk.domain.Token;
import com.idk.utils.Pair;
import com.idk.utils.Status;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private SaltGenerator saltGenerator;
    private IStringEncoder encoder;

    @Inject
    public AccountServiceImpl(SaltGenerator saltGenerator, IStringEncoder encoder) {
        this.saltGenerator = saltGenerator;
        this.encoder = encoder;
    }

    @Override
    public Pair<Status, Token> register(Credentials credentials) {
        Account account = Account.find(credentials.getLogin());
        if (account != null)
            return Pair.of(Status.of(HttpServletResponse.SC_BAD_REQUEST, "Account already exists"), null);

        byte[] salt = saltGenerator.generate();
        account = new Account();
        account.setLogin(credentials.getLogin());
        String passwordHashed = encoder.encodeWithSalt(credentials.getPassword().getBytes(), salt);
        account.setPassword(passwordHashed);
        account.setSalt(encoder.encodeBase64(salt));
        account.setDeleted(false);
        account.save();
        return Pair.of(Status.success(), createTokenAndSave(account));
    }

    @Override
    public Pair<Status, Token> authenticate(Credentials credentials) {
        Account account = Account.find(credentials.getLogin());
        if (account == null ||
            !encoder.encodeWithSalt(
                credentials.getPassword().getBytes(),
                encoder.decodeBase64(account.getSalt()))
                .equals(account.getPassword()))
            return Pair.of(Status.of(HttpServletResponse.SC_BAD_REQUEST, "Wrong account credentials"), null);


        List<Token> tokens = Token.findByLogin(account.getLogin());
        Optional<Token> found = tokens.stream().filter(token -> !token.isExpired()).findFirst();

        return Pair.of(Status.success(), found.orElse(createTokenAndSave(account)));

    }

    @Override
    public Pair<Status, String> authenticate(String token) {
        Token found = Token.find(token);
        if (Token.find(token) == null || found.isExpired())
            return Pair.of(Status.of(HttpServletResponse.SC_NOT_FOUND, "Not Found"), null);

        return Pair.of(Status.success(), found.getAccount().getLogin());
    }

    private Token createTokenAndSave(Account account) {
        LocalDateTime creation = LocalDateTime.now();
        Long hashes = (long)creation.hashCode() << 32
            ^ (long) account.getSalt().hashCode() << 16
            ^ account.getLogin().hashCode();
        String tokenString = encoder.encodeWithoutSalt(ByteBuffer
            .allocate(16)
            .putLong(hashes)
            .array());

        System.out.println(tokenString.length());
        Token token = new Token();
        token.setAccount(account);
        token.setExpired(false);
        token.setCreationDate(creation);
        token.setToken(tokenString);
        token.save();
        return token;
    }
}
