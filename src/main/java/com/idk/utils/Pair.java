package com.idk.utils;

public class Pair<K, V> {
    private final K left;
    private final V right;

    public static <K,V> Pair<K,V> of(K left, V right) {
        return new Pair<>(left, right);
    }

    public Pair(K left, V right) {
        this.left = left;
        this.right = right;
    }

    public K left() {
        return left;
    }

    public V right() {
        return right;
    }
}
