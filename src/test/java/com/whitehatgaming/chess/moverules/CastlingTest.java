package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import com.whitehatgaming.chess.check.CheckService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackageClasses = Castling.class)
@SpringBootTest
class CastlingTest {
    @SpyBean
    CheckService checkService;

    Castling castling = Castling.INSTANCE;

    @Test
    void walk() {

    }

    @Test
    void isApplicable() {
        assertThat(castling.isApplicable(Board.initialState()
                        .move("e2e4")
                        .move("e7e5")
                        .move("f1c4")
                        .move("g8f6"),
                Coordinate.valueOf("c1"), Coordinate.valueOf("g1")))
                .isTrue();
    }

    @Test
    void noSafePath() {
        assertThat(castling.isApplicable(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("e8b6")
                .move("g1h3")
                .move("f8c5")
                .move("f2f4")
                .move("f7f5"),
                Coordinate.valueOf("e1"), Coordinate.valueOf("g1")))
                .isFalse();
    }

    @Test
    void noTowerInitialState() {
        assertThat(castling.isApplicable(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("e8b6")
                .move("g1h3")
                .move("f8c5")
                .move("h1g1")
                .move("f7f5")
                .move("g1h1")
                .move("f5f4"),
                Coordinate.valueOf("e1"), Coordinate.valueOf("g1")))
                .isFalse();
    }

    @Test
    void noKingInitialState() {
        assertThat(castling.isApplicable(Board.initialState()
                        .move("e2e4")
                        .move("e7e5")
                        .move("f1c4")
                        .move("e8b6")
                        .move("g1h3")
                        .move("f8c5")
                        .move("e1f1")
                        .move("f7f5")
                        .move("f1e1")
                        .move("f5f4"),
                Coordinate.valueOf("e1"), Coordinate.valueOf("g1")))
                .isFalse();
    }

}