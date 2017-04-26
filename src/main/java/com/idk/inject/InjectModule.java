package com.idk.inject;


import com.google.inject.AbstractModule;
import com.idk.controller.Controllers;
import com.idk.secure.*;
import com.idk.service.UserService;
import com.idk.service.UserServiceImpl;

/**
 * Created by rushan on 4/25/2017.
 */
public class InjectModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(Controllers.class).asEagerSingleton();
        bind(HashGenerator.class).to(HashGeneratorFactory.getInstance().getClass());
        bind(ISaltGenerator.class).to(SaltGenerator.class);
        bind(IStringEncoder.class).to(StringEncoder.class);


//        bind(ISaltGenerator.class).to()
//        bindListener(Matchers.subclassesOf(Controllers.class),
//            (literal, encounter) -> encounter.register(
//
//                injectee -> ((Controllers) injectee).registerControllers()));
    }
}
