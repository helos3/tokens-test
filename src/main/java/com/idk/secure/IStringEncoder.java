package com.idk.secure;

public interface IStringEncoder {
    String encodeWithSalt(byte[] original, byte[] salt);
    String encodeWithoutSalt(byte[] original);
    String encodeBase64(byte[] input);
    byte[] decodeBase64(String input);
}
