package com.whitehatgaming.chess.moverules;

import com.whitehatgaming.chess.IntStreams;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import com.whitehatgaming.chess.check.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.whitehatgaming.chess.moverules.MoveRules.isInitialState;
import static com.whitehatgaming.chess.moverules.MoveRules.sameHorizontal;

public enum Castling implements MoveRule {
    INSTANCE;

    @Component
    @RequiredArgsConstructor
    private static class CheckServiceInjector {
        private final CheckService checkService;

        @PostConstruct
        public void postConstruct() {
            INSTANCE.checkService = checkService;
        }
    }

    private CheckService checkService;

    private final static Map<Coordinate, Coordinate> LINK_ROOK = Map.of(
            Coordinate.valueOf("e1"), Coordinate.valueOf("a1"),
            Coordinate.valueOf("g1"), Coordinate.valueOf("h1"),
            Coordinate.valueOf("e8"), Coordinate.valueOf("a8"),
            Coordinate.valueOf("g8"), Coordinate.valueOf("h8"));

    @Override
    public List<Coordinate> walk(Coordinate from, Coordinate to) {
        Coordinate linkRook = LINK_ROOK.get(to);
        return IntStreams.range(from.getZeroIndexColumn(), linkRook.getZeroIndexColumn())
                .skip(1)
                .mapToObj(column -> Coordinate.fromZeroIndex(column, from.getZeroIndexRow()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isApplicable(Board board, Coordinate from, Coordinate to) {
        return !board.isInitialState() &&
                sameHorizontal(from, to) &&
                LINK_ROOK.containsKey(to) &&
                isInitialState(board, from) &&
                rookInInitialState(board, LINK_ROOK.get(to)) &&
                safeKingPath(board, from, to);
    }

    private boolean rookInInitialState(Board board, Coordinate linkRook) {
        return board.findPiece(linkRook).filter(rook -> isInitialState(board, linkRook)).isPresent();
    }

    private boolean safeKingPath(Board board, Coordinate from, Coordinate to) {
        Piece.Color kingColor = board.getPiece(from).getColor();
        return IntStreams.rangeClosed(from.getZeroIndexColumn(), to.getZeroIndexColumn())
                .mapToObj(column -> Coordinate.fromZeroIndex(column, from.getZeroIndexRow()))
                .noneMatch(coordinate -> checkService.canCapture(board, kingColor.change(), coordinate));
    }
}
