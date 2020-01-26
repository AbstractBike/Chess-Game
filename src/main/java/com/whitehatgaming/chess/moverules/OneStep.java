package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;

import java.util.List;

public enum OneStep implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return List.of(to);
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return !to.equals(from) &&
                Math.abs(from.getZeroIndexX() - to.getZeroIndexX()) <= 1 &&
                Math.abs(from.getZeroIndexY() - to.getZeroIndexY()) <= 1;
    }
}
