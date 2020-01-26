package com.whitehatgaming.chess;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class Streams {
    public static <T> T throwingMerger(T t, T t1) {
        throw new UnsupportedOperationException();
    }

    public static <T> Stream<T> sequentialStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
