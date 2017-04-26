package com.idk.secure;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by berlogic on 26.04.17.
 */
public class SaltGenerator implements ISaltGenerator {
    @Override
    public byte[] generate() {
        return new SecureRandom().generateSeed(16);
    }


}
