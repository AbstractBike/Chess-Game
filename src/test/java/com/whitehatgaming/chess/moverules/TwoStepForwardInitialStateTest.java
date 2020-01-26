package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;
import com.whitehatgaming.chess.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TwoStepForwardInitialStateTest {
    private final TwoStepForwardInitialState twoStepForwardInitialState = TwoStepForwardInitialState.INSTANCE;

    @Mock
    Board board;

    @Test
    void walk() {
        Coordinate to = Coordinate.valueOf("b4");
        assertThat(twoStepForwardInitialState.walk(Coordinate.valueOf("b2"), to))
                .containsExactly(Coordinate.valueOf("b3"), to);
    }

    @Test
    void isApplicable() {
        Coordinate from = Coordinate.valueOf("b2");
        Coordinate to = Coordinate.valueOf("b4");

        when(board.getPiece(from)).thenReturn(Piece.WHITE_PAWN);
        when(board.findPiece(to)).thenReturn(Optional.empty());

        assertThat(twoStepForwardInitialState.isApplicable(board, from, to)).isTrue();
    }

    @Test
    void isNotApplicable() {

        Coordinate from = Coordinate.valueOf("b3");
        Coordinate to = Coordinate.valueOf("b5");

        when(board.getPiece(from)).thenReturn(Piece.WHITE_PAWN);
        when(board.findPiece(to)).thenReturn(Optional.empty());

        assertThat(twoStepForwardInitialState.isApplicable(board, from, to)).isFalse();
    }
}