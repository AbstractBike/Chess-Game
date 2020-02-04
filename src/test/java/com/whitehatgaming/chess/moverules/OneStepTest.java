package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class OneStepTest {
    private final OneStep oneStep = OneStep.INSTANCE;

    @Mock
    Board board;

    @Test
    void walk() {
        Coordinate to = Coordinate.valueOf("a6");
        assertThat(oneStep.walk(Coordinate.valueOf("a5"), to)).containsExactly(to);
    }

    @Test
    void isApplicable() {
        assertThat(oneStep.isApplicable(board, Coordinate.valueOf("a6"), Coordinate.valueOf("a5"))).isTrue();
    }

    @Test
    void isNotApplicable() {
        assertThat(oneStep.isApplicable(board, Coordinate.valueOf("a6"), Coordinate.valueOf("a7"))).isTrue();
    }
}