package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.Coordinate;
import com.whitehatgaming.chess.IntStreams;

import java.util.List;
import java.util.stream.Collectors;

import static com.whitehatgaming.chess.moverules.MoveRules.sameVertical;

public enum Vertical implements MoveRule {
    INSTANCE;

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        return IntStreams.rangeClosed(from.getZeroIndexRow(), to.getZeroIndexRow()).skip(1)
                .mapToObj(row -> Coordinate.fromZeroIndex(from.getZeroIndexColumn(), row))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return sameVertical(from, to);
    }
}
