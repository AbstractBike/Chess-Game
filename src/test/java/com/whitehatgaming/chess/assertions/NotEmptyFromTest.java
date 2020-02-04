package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Move;
import com.whitehatgaming.chess.game.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotEmptyFromTest {
    private final NotEmptyFrom notEmptyFrom = new NotEmptyFrom();

    @Mock
    State state;
    @Mock
    Board board;

    @Test
    void assertLegal() {
        Move b2c3 = Move.valueOf("b2c3");
        when(state.getBoard()).thenReturn(board);
        when(board.findPiece(b2c3.getFrom())).thenReturn(Optional.of(Piece.BLACK_PAWN));

        assertThat(notEmptyFrom.assertLegal(state, b2c3)).isEmpty();
    }

    @Test
    void assertIllegal() {
        Move b2c3 = Move.valueOf("b2c3");
        when(state.getBoard()).thenReturn(board);
        when(board.findPiece(b2c3.getFrom())).thenReturn(Optional.empty());

        assertThat(notEmptyFrom.assertLegal(state, b2c3))
                .containsInstanceOf(PieceNotFoundException.class);
    }
}