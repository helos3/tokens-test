package com.idk.secure;

import javax.inject.Inject;
import java.util.Base64;

public class StringEncoder implements IStringEncoder {

    private HashGenerator hashGenerator;

    @Override
    public String encodeWithoutSalt(byte[] original) {
        return encodeBase64(hashGenerator.hash(original));
    }

    @Inject
    public StringEncoder(HashGenerator hashGenerator) {
        this.hashGenerator = hashGenerator;
    }


    @Override
    public String encodeWithSalt(byte[] original, byte[] salt) {

        //hash(hash(original) XOR salt)
        return encodeBase64(hashGenerator.hash(xorWithKey(hashGenerator.hash(original), salt)));
    }

    @Override
    public String encodeBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    @Override
    public byte[] decodeBase64(String input) {
        return Base64.getDecoder().decode(input);
    }


    private byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i%key.length]);
        }
        return out;
    }

}
