package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;

import java.util.List;

public enum L implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return List.of(to);
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return (Math.abs(from.getZeroIndexX() - to.getZeroIndexX()) == 1 &&
                Math.abs(from.getZeroIndexY() - to.getZeroIndexY()) == 2) ||
                (Math.abs(from.getZeroIndexY() - to.getZeroIndexY()) == 1 &&
                        Math.abs(from.getZeroIndexX() - to.getZeroIndexX()) == 2);
    }
}
