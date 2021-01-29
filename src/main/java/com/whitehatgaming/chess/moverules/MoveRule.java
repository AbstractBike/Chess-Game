package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import com.whitehatgaming.chess.board.Move;

import java.util.List;

public interface MoveRule {
    List<Coordinate> walk(Coordinate from, Coordinate to);

    boolean isApplicable(Board board, Coordinate from, Coordinate to);

    default Board move(Board board, Coordinate from, Coordinate to) {
        return board.move(Move.of(from, to));
    }
}
