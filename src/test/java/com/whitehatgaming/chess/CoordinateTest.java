package com.whitehatgaming.chess;

import com.whitehatgaming.chess.board.Coordinate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinateTest {

    @Test
    void fromZeroIndex() {
        assertThat(Coordinate.fromZeroIndex(0, 0))
                .isEqualTo(Coordinate.valueOf("a1"));
    }

    @Test
    void getZeroIndexClumn() {
        assertThat(Coordinate.valueOf("h8").getZeroIndexColumn())
                .isEqualTo(7);
    }

    @Test
    void getZeroIndexRow() {
        assertThat(Coordinate.valueOf("h8").getZeroIndexRow())
                .isEqualTo(7);
    }

    @Test
    void ofString() {
        assertThat(Coordinate.valueOf("d3")).isEqualTo(Coordinate.create('d', 3));
    }

    @Test
    void testToString() {
        assertThat(Coordinate.valueOf("d3").toString()).isEqualTo("d3");
    }
}