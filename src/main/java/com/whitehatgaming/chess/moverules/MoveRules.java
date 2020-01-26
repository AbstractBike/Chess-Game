package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;
import com.whitehatgaming.chess.Piece;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.IntBinaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MoveRules {
    private static final Map<Piece.Color, IntBinaryOperator> FORWARD = Map.of(
            Piece.Color.WHITE, Integer::sum,
            Piece.Color.BLACK, (from, steps) -> from - steps);

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
}
