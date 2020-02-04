package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Move;
import com.whitehatgaming.chess.game.State;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@Component
public class PieceLegalMove implements PreAssertion {
    private final int order = 4;

    @Override
    public Optional<RuntimeException> assertLegal(State lastState, Move move) {

        Board board = lastState.getBoard();
        Piece piece = board.getPiece(move.getFrom());

        if (!piece.legalMove(board, move.getFrom(), move.getTo())) {
            return Optional.of(new IllegalMoveException("Illegal move:" + move));
        }

        return Optional.empty();
    }
}
