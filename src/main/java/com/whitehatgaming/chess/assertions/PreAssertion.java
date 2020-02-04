package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.board.Move;
import com.whitehatgaming.chess.game.State;

import java.util.Optional;

public interface PreAssertion {
    Optional<RuntimeException> assertLegal(State lastState, Move move);

    int getOrder();
}
