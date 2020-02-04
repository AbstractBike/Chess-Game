package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.IntStreams;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

import static com.whitehatgaming.chess.moverules.MoveRules.isCapturing;
import static com.whitehatgaming.chess.moverules.MoveRules.isInitialState;
import static com.whitehatgaming.chess.moverules.MoveRules.sameVertical;
import static com.whitehatgaming.chess.moverules.MoveRules.stepsForwards;

public enum TwoStepForwardInitialState implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return IntStreams.rangeClosed(from.getZeroIndexRow(), to.getZeroIndexRow()).skip(1)
                .mapToObj(row -> Coordinate.fromZeroIndex(from.getZeroIndexColumn(), row))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return !isCapturing(board, to) &&
                isInitialState(board, from) &&
                stepsForwards(board, from, 2) == to.getZeroIndexRow() &&
                sameVertical(from, to);
    }

}
