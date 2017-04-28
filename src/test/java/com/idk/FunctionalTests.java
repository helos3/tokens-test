package com.idk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.inject.Guice;
import com.idk.domain.Account;
import com.idk.inject.InjectModule;
import com.idk.utils.Unchecked;
import io.ebean.Ebean;
import io.ebean.dbmigration.migration.*;
import io.ebean.dbmigration.migration.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.h2.engine.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.Access;
import spark.Spark;
import spark.utils.IOUtils;

/**
 * Created by rushan on 4/27/2017.
 */
public class FunctionalTests {

    @Before
    public void init() {
        Spark.port(9999);
        Guice.createInjector(new InjectModule());
//        Ebean.find()
//        Ebean.deleteAll(Arrays.asList(Account.class, Token.class));
    }

    @Test
    public void loginByCredentialsSuccessScenario() {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost register = new HttpPost("http://localhost:9999/register");
        register.setHeader("Content-Type", "application/json");
        HttpEntity entity = new StringEntity("{\"login\":\"user2\", \"password\":\"user2\"}", Charsets.UTF_8);
        register.setEntity(entity);

        HttpResponse response = Unchecked.call(() -> client.execute(register));
        JSONObject responseJson = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(response.getEntity().getContent())));
        Assert.assertNotNull(responseJson);
        Assert.assertNotNull(responseJson.get("token"));
        System.out.println(responseJson.get("token"));



    }

    @Test
    public void loginByWrongCredentialsScenario() {

    }

    @Test
    public void loginByNotExistingLoginScenario() {

    }
    @Test
    public void loginByTokenSuccessScenario() {

    }
}
