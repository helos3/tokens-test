package com.idk.domain;

import io.ebean.Model;


/**
 * Created by berlogic on 26.04.17.
 */
public class BaseModel<ID> extends Model {

    protected static <T extends BaseModel<ID>, ID> T find(Class<T> clazz, ID primaryKey) {
        return db().find(clazz, primaryKey);
    }


}
