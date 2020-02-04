package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;

import java.util.List;

import static com.whitehatgaming.chess.moverules.MoveRules.isCapturing;
import static com.whitehatgaming.chess.moverules.MoveRules.stepsForwards;

public enum CapturingOneStepForwardDiagonal implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return List.of(to);
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return isCapturing(board, to) &&
                stepsForwards(board, from, 1) == to.getZeroIndexRow() &&
                Math.abs(to.getZeroIndexColumn() - from.getZeroIndexColumn()) == 1;
    }


}
