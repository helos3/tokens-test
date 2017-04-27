package com.idk;

import com.google.inject.Guice;
import com.idk.inject.InjectModule;
import com.idk.utils.Unchecked;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.Properties;

/**
 * Created by berlogic on 27.04.17.
 */
public class Application {
    public static void main(String[] args) {
        //to trigger injecting and launch services
        Guice.createInjector(new InjectModule());
    }
}