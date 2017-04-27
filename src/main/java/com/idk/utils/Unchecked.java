package com.idk.utils;

import java.util.concurrent.Callable;


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
