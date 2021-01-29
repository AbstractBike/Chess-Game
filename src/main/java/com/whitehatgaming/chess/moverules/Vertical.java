package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.IntStreams;
import com.whitehatgaming.chess.Streams;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;

import java.util.List;
import java.util.Set;
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

    public Set<Coordinate> possibleMoves(Board board, Coordinate from) {
        return Streams.concat(IntStreams.rangeClosed(1, from.getZeroIndexColumn())
                        .filter(i -> from.getZeroIndexColumn() - i >= 0)
                        .filter(i -> from.getZeroIndexRow() - i >= 0)
                        .mapToObj(i -> Coordinate.fromZeroIndex(from.getZeroIndexColumn() - i, from.getZeroIndexRow() - i)),
                IntStreams.rangeClosed(1, from.getZeroIndexColumn())
                        .filter(i -> from.getZeroIndexColumn() - i >= 0)
                        .filter(i -> from.getZeroIndexRow() + i >= 0)
                        .mapToObj(i -> Coordinate.fromZeroIndex(from.getZeroIndexColumn() - i, from.getZeroIndexRow() + i)),
                IntStreams.rangeClosed(1, from.getZeroIndexRow())
                        .filter(i -> from.getZeroIndexColumn() + i >= 0)
                        .filter(i -> from.getZeroIndexRow() - i >= 0)
                        .mapToObj(i -> Coordinate.fromZeroIndex(from.getZeroIndexColumn() + i, from.getZeroIndexRow() - i)),
                IntStreams.rangeClosed(from.getZeroIndexRow(), 0)
                        .filter(i -> from.getZeroIndexColumn() + i >= 0)
                        .filter(i -> from.getZeroIndexRow() + i >= 0)
                        .mapToObj(i -> Coordinate.fromZeroIndex(from.getZeroIndexColumn() + i, from.getZeroIndexRow() + i)))
                .collect(Collectors.toUnmodifiableSet());

    }
}
