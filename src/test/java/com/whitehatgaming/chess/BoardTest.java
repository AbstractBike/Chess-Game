package com.whitehatgaming.chess;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.function.Function;

import static com.whitehatgaming.chess.Piece.Color.BLACK;
import static com.whitehatgaming.chess.Piece.Color.WHITE;
import static com.whitehatgaming.chess.Piece.WHITE_KING;
import static com.whitehatgaming.chess.Piece.WHITE_KNIGHT;
import static com.whitehatgaming.chess.Piece.WHITE_PAWN;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @EnumSource(Piece.class)
    @ParameterizedTest
    void initialStatePieces(Piece piece) {
        Board initialState = Board.initialState();

        assertThat(initialState.getCoordinates(piece)).isEqualTo(piece.getInitialCoordinates());
    }

    @EnumSource(Piece.class)
    @ParameterizedTest
    void initialStateCoordinates(Piece piece) {
        Board initialState = Board.initialState();

        assertThat(piece.getInitialCoordinates())
                .allMatch(c -> initialState.findPiece(c).orElseThrow().equals(piece));
    }

    @Test
    void getPiece() {
        assertThat(Board.initialState().findPiece(Coordinate.valueOf("e1"))).isPresent().get()
                .isEqualTo(WHITE_KING);
    }

    @Test
    void move() {
        assertThat(Board.initialState()
                .move("a2a4").findPiece(Coordinate.valueOf("a4"))).isPresent()
                .get().isEqualTo(WHITE_PAWN);
    }

    @Test
    void capture() {
        assertThat(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f2f4")
                .move("e5f4").getCoordinates(WHITE_PAWN))
                .hasSize(7);
    }

    @Test
    void testToString() {
        assertThat(Board.initialState()
                .move("a2a4")
                .toString()).isEqualTo(
                "8\t r\tn\tb\tq\tk\tb\tn\tr\n" +
                        "7\t p\tp\tp\tp\tp\tp\tp\tp\n" +
                        "6\t -\t*\t-\t*\t-\t*\t-\t*\n" +
                        "5\t *\t-\t*\t-\t*\t-\t*\t-\n" +
                        "4\t P\t*\t-\t*\t-\t*\t-\t*\n" +
                        "3\t *\t-\t*\t-\t*\t-\t*\t-\n" +
                        "2\t -\tP\tP\tP\tP\tP\tP\tP\n" +
                        "1\t R\tN\tB\tQ\tK\tB\tN\tR\n" +
                        "\n" +
                        "\t a\tb\tc\td\te\tf\tg\th");
    }

    @Test
    void getTurn() {

        Board initialState = Board.initialState();
        Board e2e4 = initialState.move("e2e4");
        Board e7a5 = e2e4.move("e7a5");

        assertThat(initialState.getNextTurn()).isEqualTo(WHITE);
        assertThat(e2e4.getNextTurn()).isEqualTo(BLACK);
        assertThat(e7a5.getNextTurn()).isEqualTo(WHITE);
    }

    @Test
    void getCoordinates() {
        assertThat(Board.initialState().move("b1a3").getCoordinates(WHITE_KNIGHT))
                .containsExactlyInAnyOrder(Coordinate.valueOf("a3"), Coordinate.valueOf("g1"));
    }

    @EnumSource(Piece.Color.class)
    @ParameterizedTest
    void getPieces(Piece.Color color) {
        assertThat(Board.initialState().getPieces(color))
                .isEqualTo(Arrays.stream(Piece.values()).filter(piece -> piece.getColor().equals(color))
                        .collect(toMap(Function.identity(), Piece::getInitialCoordinates)));
    }
}