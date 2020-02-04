package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.IntStreams;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

public enum Diagonal implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return IntStreams.rangeClosed(from.getZeroIndexColumn(), to.getZeroIndexColumn()).skip(1)
                .mapToObj(column -> Coordinate.fromZeroIndex(column, getRow(from, to, Math.abs(column - from.getZeroIndexColumn()))))
                .collect(Collectors.toUnmodifiableList());
    }

    private int getRow(Coordinate from, Coordinate to, int step) {
        return to.getZeroIndexRow() - from.getZeroIndexRow() > 0 ?
                from.getZeroIndexRow() + step :
                from.getZeroIndexRow() - step;
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return Math.abs(from.getZeroIndexColumn() - to.getZeroIndexColumn()) ==
                Math.abs(from.getZeroIndexRow() - to.getZeroIndexRow());
    }
}
