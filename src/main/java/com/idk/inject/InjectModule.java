package com.idk.inject;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.idk.controller.Controllers;
import com.idk.secure.*;
import com.idk.service.UserService;
import com.idk.service.UserServiceImpl;

import java.security.SecureRandom;

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
        requestInjection(this);
        bind(Controllers.class).asEagerSingleton();
        bind(UserService.class).to(UserServiceImpl.class);
        bind(IStringEncoder.class).to(StringEncoder.class);
    }
}
