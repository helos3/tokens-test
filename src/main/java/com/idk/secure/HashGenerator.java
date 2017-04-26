package com.idk.secure;

import java.util.stream.StreamSupport;

/**
 * Created by berlogic on 26.04.17.
 */
public interface HashGenerator {
    byte[] hash(byte[] input);
}
