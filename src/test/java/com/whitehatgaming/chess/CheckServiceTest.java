package com.whitehatgaming.chess;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CheckServiceTest {

    private final CheckService checkService = new CheckService();

    @Test
    void isKingInCheckPosition() {
        assertThat(checkService.isKingInCheckPosition(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")
                .move("d7d6")
                .move("f3f7")
                .move("e8e7"), true, Coordinate.valueOf("e7")))
                .isTrue();
    }

    @Test
    void isNotKingInCheckPosition() {
        assertThat(checkService.isKingInCheckPosition(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3"), false, Coordinate.valueOf("f3")))
                .isFalse();
    }

    @Test
    void isCheck() {
        assertThat(checkService.isCheck(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")
                .move("d7d6")
                .move("f3f7"), Coordinate.valueOf("f7")))
                .isTrue();
    }

    @Test
    void isNotCheck() {
        assertThat(checkService.isCheck(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3"), Coordinate.valueOf("f3")))
                .isFalse();
    }
}