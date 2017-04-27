package com.idk.secure;

import java.util.stream.StreamSupport;

public interface HashGenerator {
    byte[] hash(byte[] input);
}
