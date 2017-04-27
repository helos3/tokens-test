package com.idk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.idk.utils.Credentials;
import com.idk.domain.Token;
import com.idk.service.UserService;
import com.idk.utils.Pair;
import com.idk.utils.Status;
import spark.Spark;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class Controllers {
    private final UserService userService;


    @Inject
    public Controllers(UserService userService) {
        this.userService = userService;
        registerControllers();
    }


    private void registerControllers() {

        Spark.post("/register", (req, res) -> {

            Credentials credentials = JSON.parseObject(req.body(), Credentials.class);

            Pair<Status, Token> result = credentials.areCorrect() ?
                userService.register(credentials) :
                Pair.of(
                    Status.of(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Wrong credentials"),
                    null);

            JSONObject json = new JSONObject();
            res.status(result.left().code());

            if (result.left().isFailure()) {
                json.put("error message", result.left().message());
            } else {
                json.put("token", result.right().getToken());
            }
            return json.toJSONString();
        });


        Spark.post("/token", (req, res) -> {

            Credentials credentials = JSON.parseObject(req.body(), Credentials.class);

            Pair<Status, Token> result = credentials.areCorrect() ?
                userService.authenticate(credentials) :
                Pair.of(
                    Status.of(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Wrong credentials"),
                    null);

            JSONObject json = new JSONObject();
            res.status(result.left().code());

            if (result.left().isFailure()) {
                json.put("error message", result.left().message());
            } else {
                json.put("token", result.right().getToken());
            }

            return json.toJSONString();
        });

        Spark.post("/login", (req, res) -> {

            String inputToken = JSON.parseObject(req.body()).getString("token");

            Pair<Status, String> result = inputToken != null && inputToken.length() > 10 ?
                userService.authenticate(inputToken) :
                Pair.of(
                    Status.of(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Wrong token"),
                    null);


            JSONObject json = new JSONObject();
            res.status(result.left().code());

            if (result.left().isFailure()) {
                json.put("error message", result.left().message());
            } else {
                json.put("login", result.right());
            }
            return json.toJSONString();
        });
    }

}
