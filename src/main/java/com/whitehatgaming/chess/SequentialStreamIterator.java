package com.whitehatgaming.chess;

import java.util.Iterator;
import java.util.stream.Stream;

interface SequentialStreamIterator<T> extends Iterator<T> {
    default Stream<T> stream() {
        return Streams.sequentialStream(() -> this);
    }
}
