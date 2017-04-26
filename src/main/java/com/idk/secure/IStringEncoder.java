package com.idk.secure;

/**
 * Created by berlogic on 26.04.17.
 */
public interface IStringEncoder {
    String encodeWithSalt(byte[] original, byte[] salt);
    String encodeBase64(byte[] input);
    byte[] decodeBase64(String input);
}
