package com.idk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.inject.Guice;
import com.idk.domain.Account;
import com.idk.domain.Token;
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
import org.junit.*;
import spark.Access;
import spark.Spark;
import spark.utils.IOUtils;

/**
 * Created by rushan on 4/27/2017.
 */
public class FunctionalTests {

    @BeforeClass
    public static void init() {
        Spark.stop();
        Spark.port(9999);
        Guice.createInjector(new InjectModule());
        Ebean.find(Token.class).findList().forEach(Token::deletePermanent);
        Ebean.find(Account.class).findList().forEach(Account::deletePermanent);
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
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        String tokenAfterRegister = (String) responseJson.get("token");

        HttpPost login = new HttpPost("http://localhost:9999/token");
        login.setHeader("Content-Type", "application/json");
        login.setEntity(entity);


        HttpResponse responseLogin = Unchecked.call(() -> client.execute(login));
        JSONObject responseJsonLogin = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(responseLogin.getEntity().getContent())));
        Assert.assertNotNull(responseJsonLogin);
        Assert.assertNotNull(responseJsonLogin.get("token"));
        Assert.assertEquals(tokenAfterRegister, responseJsonLogin.getString("token"));
        Assert.assertEquals(200, responseLogin.getStatusLine().getStatusCode());

    }

    @Test
    public void loginByWrongCredentialsScenario() {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost register = new HttpPost("http://localhost:9999/register");
        register.setHeader("Content-Type", "application/json");
        HttpEntity entity = new StringEntity("{\"login\":\"user3\", \"password\":\"user3\"}", Charsets.UTF_8);
        register.setEntity(entity);

        HttpResponse response = Unchecked.call(() -> client.execute(register));
        JSONObject responseJson = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(response.getEntity().getContent())));
        Assert.assertNotNull(responseJson);
        Assert.assertNotNull(responseJson.get("token"));
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        String tokenAfterRegister = (String) responseJson.get("token");

        HttpPost login = new HttpPost("http://localhost:9999/token");
        login.setHeader("Content-Type", "application/json");
        HttpEntity badCredentialsEntity = new StringEntity("{\"login\":\"user3\", \"password\":\"user33\"}", Charsets.UTF_8);

        login.setEntity(badCredentialsEntity);


        HttpResponse responseLogin = Unchecked.call(() -> client.execute(login));
        JSONObject responseJsonLogin = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(responseLogin.getEntity().getContent())));
        Assert.assertNotNull(responseJsonLogin);
        Assert.assertNotNull(responseJsonLogin.get("error message"));
        Assert.assertEquals(400, responseLogin.getStatusLine().getStatusCode());

    }

    @Test
    public void loginByNotExistingLoginScenario() {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost register = new HttpPost("http://localhost:9999/login");
        register.setHeader("Content-Type", "application/json");
        HttpEntity entity = new StringEntity("{\"login\":\"user4\", \"password\":\"user4\"}", Charsets.UTF_8);
        register.setEntity(entity);

        HttpResponse response = Unchecked.call(() -> client.execute(register));
        JSONObject responseJson = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(response.getEntity().getContent())));
        Assert.assertNotNull(responseJson);
        Assert.assertNotNull(responseJson.get("error message"));
        Assert.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void loginByTokenSuccessScenario() {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost register = new HttpPost("http://localhost:9999/register");
        register.setHeader("Content-Type", "application/json");
        HttpEntity entity = new StringEntity("{\"login\":\"user5\", \"password\":\"user5\"}", Charsets.UTF_8);
        register.setEntity(entity);

        HttpResponse response = Unchecked.call(() -> client.execute(register));
        JSONObject responseJson = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(response.getEntity().getContent())));
        Assert.assertNotNull(responseJson);
        Assert.assertNotNull(responseJson.get("token"));
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        String tokenAfterRegister = (String) responseJson.get("token");

        HttpPost token = new HttpPost("http://localhost:9999/login");
        token.setHeader("Content-Type", "application/json");
        HttpEntity tokenEntity = new StringEntity("{\"token\":\""+tokenAfterRegister+"\"}", Charsets.UTF_8);
        token.setEntity(tokenEntity);


        HttpResponse responseLogin = Unchecked.call(() -> client.execute(token));
        JSONObject responseJsonLogin = JSON.parseObject(Unchecked.call(() -> IOUtils.toString(responseLogin.getEntity().getContent())));
        Assert.assertNotNull(responseJsonLogin);
        Assert.assertNotNull(responseJsonLogin.get("login"));
        Assert.assertEquals("user5", responseJsonLogin.get("login"));
        Assert.assertEquals(200, responseLogin.getStatusLine().getStatusCode());

    }


    @AfterClass
    public static void stop() {
        Spark.stop();
    }
}
