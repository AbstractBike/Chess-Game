package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.game.State;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Component
public class RightColor implements PreAssertion {
    private final int order = 1;

    @Override
    public Optional<RuntimeException> assertLegal(State lastState, Move move) {

        Piece piece = lastState.getBoard().getPiece(move.getFrom());

        if (!piece.getColor().equals(lastState.getBoard().getNextTurn())) {
            return Optional.of(new IncorrectTurn("Incorrect color:" + piece.getColor()));
        }

        return Optional.empty();
    }
}
