package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;

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
                stepsForwards(board, from, 1) == to.getZeroIndexY() &&
                Math.abs(to.getZeroIndexX() - from.getZeroIndexX()) == 1;
    }


}
