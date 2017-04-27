package com.idk.secure;

import com.idk.utils.Unchecked;

import java.security.MessageDigest;

public class HashGeneratorFactory {

    public static HashGenerator getInstance() {
        return getInstance(HashAlgorithm.SHA512);
    }

    public static HashGenerator getInstance(HashAlgorithm algorithm) {
        return input ->
            Unchecked.call(() -> MessageDigest.getInstance(algorithm.name))
                .digest(input);
    }

    public static enum HashAlgorithm {
        SHA512("SHA-512"), SHA384("SHA-384"), MD5("MD5");

        HashAlgorithm(String name) {
            this.name = name;
        }

        String name;
    }
}

