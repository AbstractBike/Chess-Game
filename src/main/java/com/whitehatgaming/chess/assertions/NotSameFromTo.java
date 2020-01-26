package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.game.State;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Component
public class NotSameFromTo implements PreAssertion {
    private final int order = 3;

    @Override
    public Optional<RuntimeException> assertLegal(State lastState, Move move) {
        if (move.getFrom().equals(move.getTo())) {
            return Optional.of(new NotMoveException("The piece needs to be moved"));
        }
        return Optional.empty();
    }
}
