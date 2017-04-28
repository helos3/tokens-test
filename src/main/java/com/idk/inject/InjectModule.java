package com.idk.inject;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.idk.controller.Controllers;
import com.idk.secure.*;
import com.idk.service.AccountService;
import com.idk.service.AccountServiceImpl;

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
        bind(Controllers.class).asEagerSingleton();
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(IStringEncoder.class).to(StringEncoder.class);
        requestInjection(this);
    }
}
