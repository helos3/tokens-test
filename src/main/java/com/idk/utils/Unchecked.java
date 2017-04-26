package com.idk.utils;

import java.util.concurrent.Callable;

/**
 * Created by berlogic on 26.04.17.
 */

public class Unchecked {
    public static <T> T call(Callable<T> callable) {

            try {
                return callable.call();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        };


}
