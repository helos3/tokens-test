package com.idk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.idk.domain.User;
import com.idk.inject.InjectModule;
import com.idk.secure.*;
import com.idk.service.UserService;
import com.idk.service.UserServiceImpl;
import io.ebean.Ebean;
import org.junit.Test;


public class EncodingTest {

    @Test
    public void encodingTest() {
        Injector injector = Guice.createInjector(new InjectModule());
        UserService service = injector.getInstance(UserService.class);
        IStringEncoder encoder = injector.getInstance(IStringEncoder.class);
        SaltGenerator generator = injector.getInstance(SaltGenerator.class);

        String password = "q1w2e3r4";
        byte[] salt = generator.generate();

        System.out.println(encoder.encodeBase64(salt));
        System.out.println(encoder.encodeWithSalt(password.getBytes(), salt));

        System.out.println(encoder.encodeWithSalt(password.getBytes(), salt));

        System.out.println(encoder.encodeWithSalt(password.getBytes(), encoder.decodeBase64(encoder.encodeBase64(salt))));


    }

    @Test
    public void gooseTest() {

        Injector injector = Guice.createInjector(new InjectModule());
        UserService service = injector.getInstance(UserServiceImpl.class);
        HashGenerator generator = injector.getInstance(HashGenerator.class);


    }

}
