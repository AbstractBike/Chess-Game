package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.game.State;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Component
public class NotEmptyFrom implements PreAssertion {
    private final int order = 0;

    @Override
    public Optional<RuntimeException> assertLegal(State lastState, Move move) {

        Optional<Piece> piece = lastState.getBoard().findPiece(move.getFrom());

        if (piece.isEmpty()) {
            return Optional.of(new PieceNotFoundException(move.getFrom()));
        }

        return Optional.empty();
    }
}
