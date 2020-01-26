package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;
import com.whitehatgaming.chess.IntStreams;

import java.util.List;
import java.util.stream.Collectors;

import static com.whitehatgaming.chess.moverules.MoveRules.sameHorizontal;

public enum Horizontal implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return IntStreams.rangeClosed(from.getZeroIndexX(), to.getZeroIndexX()).skip(1)
                .mapToObj(x -> Coordinate.fromZeroIndex(x, from.getZeroIndexY()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return sameHorizontal(from, to);
    }
}
