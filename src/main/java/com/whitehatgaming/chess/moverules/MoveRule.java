package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;

import java.util.List;

public interface MoveRule {
    List<Coordinate> walk(Coordinate from, Coordinate to);

    boolean isApplicable(Board board, Coordinate from, Coordinate to);


}
