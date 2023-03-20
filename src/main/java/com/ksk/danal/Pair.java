package com.ksk.danal;

public final class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public static <A,B> Pair<A,B> to(A a, B b) {
        return new Pair<>(a,b);
    }
}
