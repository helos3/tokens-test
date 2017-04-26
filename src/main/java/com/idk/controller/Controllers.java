package com.idk.controller;

import com.idk.domain.User;
import com.idk.service.UserService;
import com.idk.service.UserServiceImpl;
import io.ebean.Ebean;
import spark.Spark;

import javax.inject.Inject;

/**
 * Created by rushan on 4/25/2017.
 */
public class Controllers {
    private final UserService userService;



    @Inject
    public Controllers(UserService userService) {
        this.userService = userService;
        registerControllers();
    }


    public void registerControllers() {

        Spark.get("/user", (req, res) -> userService.createUser(Ebean.json().toBean(User.class, req.body())));
    }

}
