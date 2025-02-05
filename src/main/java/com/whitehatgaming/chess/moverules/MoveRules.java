package com.whitehatgaming.chess.moverules;

import com.google.common.collect.Maps;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.Streams;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.function.IntBinaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MoveRules {
    private static final Map<Piece.Color, IntBinaryOperator> FORWARD = Collections.unmodifiableMap(Maps.newEnumMap(Map.of(
            Piece.Color.WHITE, Integer::sum,
            Piece.Color.BLACK, (from, steps) -> from - steps)));

    static boolean isCapturing(Board board, Coordinate to) {
        return board.findPiece(to).isPresent();
    }

    static boolean sameVertical(Coordinate a, Coordinate b) {
        return a.getZeroIndexColumn() == b.getZeroIndexColumn();
    }

    static boolean sameHorizontal(Coordinate a, Coordinate b) {
        return a.getZeroIndexRow() == b.getZeroIndexRow();
    }

    static int stepsForwards(Board board, Coordinate from, int steps) {
        return FORWARD.get(board.getPiece(from).getColor()).applyAsInt(from.getZeroIndexRow(), steps);
    }

    static boolean isInitialState(Board board, Coordinate from) {
        Piece piece = board.getPiece(from);
        return Streams.sequentialStream(board::historyIterator)
                .map(b -> b.getCoordinates(piece))
                .allMatch(coordinates -> coordinates.contains(from));
    }
}
