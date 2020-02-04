package com.whitehatgaming.chess;

import lombok.NoArgsConstructor;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Streams {
    public static <T> T throwingMerger(T t, T t1) {
        throw new UnsupportedOperationException();
    }

    public static <T> Stream<T> sequentialStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
