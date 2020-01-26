package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class LTest {
    private final L l = L.INSTANCE;

    @Mock
    Board board;

    @Test
    void walk() {

        Coordinate to = Coordinate.valueOf("c3");

        assertThat(l.walk(Coordinate.valueOf("a2"), to)).containsExactly(to);
    }

    @Test
    void isApplicableA2C3() {
        assertThat(l.isApplicable(board, Coordinate.valueOf("a2"), Coordinate.valueOf("c3"))).isTrue();
    }

    @Test
    void isApplicableA2B4() {
        assertThat(l.isApplicable(board, Coordinate.valueOf("a2"), Coordinate.valueOf("b4"))).isTrue();
    }

    @Test
    void isNotApplicable() {
        assertThat(l.isApplicable(board, Coordinate.valueOf("b2"), Coordinate.valueOf("c3"))).isFalse();
    }
}