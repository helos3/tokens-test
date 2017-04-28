package com.idk;

import com.google.inject.Guice;
import com.idk.inject.InjectModule;
import spark.servlet.SparkApplication;

/**
 * Created by rushan on 4/27/2017.
 */
public class WebApplication implements SparkApplication {

    @Override
    public void init() {
        Guice.createInjector(new InjectModule());
    }
}
