package com.idk.inject;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.idk.controller.Controllers;
import com.idk.secure.*;
import com.idk.service.UserService;
import com.idk.service.UserServiceImpl;

import java.security.SecureRandom;

/**
 * Created by rushan on 4/25/2017.
 */
public class InjectModule extends AbstractModule {


    @Provides
    public HashGenerator provideHashGenerator(){
        return HashGeneratorFactory.getInstance();
    }

    @Provides
    //inject functional interfaces
    //HACKERMAN
    public SaltGenerator provideSaltGenerator() {
        return () -> new SecureRandom().generateSeed(16);
    }


    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(Controllers.class).asEagerSingleton();
        bind(IStringEncoder.class).to(StringEncoder.class);
    }
}
