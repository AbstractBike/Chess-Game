package com.whitehatgaming.chess.assertions;


import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ChessException extends RuntimeException {
    private final List<RuntimeException> causes;

    @Override
    public String getMessage() {
        return IntStream.range(0, causes.size())
                .mapToObj(i -> (i + 1) + ". " + causes.get(i).getClass().getSimpleName() + ": " + causes.get(i).getLocalizedMessage())
                .collect(Collectors.joining("\n", "\n", ""));
    }
}
