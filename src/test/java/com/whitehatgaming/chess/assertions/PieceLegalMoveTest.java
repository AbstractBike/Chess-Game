package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Move;
import com.whitehatgaming.chess.game.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PieceLegalMoveTest {
    private final PieceLegalMove pieceLegalMove = new PieceLegalMove();

    @Mock
    State state;
    @Mock
    Board board;

    @Test
    void assertLegal() {
        Move b2c3 = Move.valueOf("b2c3");
        when(state.getBoard()).thenReturn(board);
        when(board.getPiece(b2c3.getFrom())).thenReturn(Piece.WHITE_PAWN);
        when(board.findPiece(b2c3.getTo())).thenReturn(Optional.of(Piece.BLACK_PAWN));

        assertThat(pieceLegalMove.assertLegal(state, b2c3)).isEmpty();
    }

    @Test
    void assertIllegal() {
        Move b2c3 = Move.valueOf("b2b5");
        when(state.getBoard()).thenReturn(board);
        when(board.getPiece(b2c3.getFrom())).thenReturn(Piece.WHITE_PAWN);
        when(board.historyIterator()).thenReturn(Collections.singleton(Board.initialState()).iterator());

        assertThat(pieceLegalMove.assertLegal(state, b2c3))
                .containsInstanceOf(IllegalMoveException.class);
    }
}