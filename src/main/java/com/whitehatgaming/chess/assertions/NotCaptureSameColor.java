package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.game.State;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Component
public class NotCaptureSameColor implements PreAssertion {
    private final int order = 2;

    @Override
    public Optional<RuntimeException> assertLegal(State lastState, Move move) {
        Piece.Color color = lastState.getBoard().getPiece(move.getFrom()).getColor();
        Optional<Piece> capturingPiece = lastState.getBoard().findPiece(move.getTo());

        if (capturingPiece.isPresent() && capturingPiece.get().getColor() == color) {
            return Optional.of(new IllegalMoveException("Cannot capture same color piece: " + move));
        }

        return Optional.empty();
    }
}
