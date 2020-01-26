package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;
import com.whitehatgaming.chess.IntStreams;

import java.util.List;
import java.util.stream.Collectors;

public enum Diagonal implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return IntStreams.rangeClosed(from.getZeroIndexX(), to.getZeroIndexX()).skip(1)
                .mapToObj(x -> Coordinate.fromZeroIndex(x, getY(from, to, Math.abs(x - from.getZeroIndexX()))))
                .collect(Collectors.toUnmodifiableList());
    }

    private int getY(Coordinate from, Coordinate to, int step) {
        return to.getZeroIndexY() - from.getZeroIndexY() > 0 ?
                from.getZeroIndexY() + step :
                from.getZeroIndexY() - step;
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return Math.abs(from.getZeroIndexX() - to.getZeroIndexX()) ==
                Math.abs(from.getZeroIndexY() - to.getZeroIndexY());
    }
}
