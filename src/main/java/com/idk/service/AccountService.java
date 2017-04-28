package com.idk.service;

import com.idk.utils.Credentials;
import com.idk.domain.Token;
import com.idk.utils.Pair;
import com.idk.utils.Status;

public interface AccountService {

    /**
     * @return Optional.empty() if user already exists
     */
    Pair<Status, Token> register(Credentials user);

    /**
     *
     * @return Optional.empty() if credentials are wrong
     */

    Pair<Status, Token> authenticate(Credentials user);


    /**
     *
     * @return Optional.empty() if token is expired or no such token exists
     */
    Pair<Status, String> authenticate(String token);
}
