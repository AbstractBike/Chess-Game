package com.whitehatgaming.chess;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.whitehatgaming.chess.Figurine.BISHOP;
import static com.whitehatgaming.chess.Figurine.KING;
import static com.whitehatgaming.chess.Figurine.KNIGHT;
import static com.whitehatgaming.chess.Figurine.PAWN;
import static com.whitehatgaming.chess.Figurine.QUEEN;
import static com.whitehatgaming.chess.Figurine.ROOK;
import static com.whitehatgaming.chess.Piece.Color.BLACK;
import static com.whitehatgaming.chess.Piece.Color.WHITE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Getter
@RequiredArgsConstructor
public enum Piece {
    WHITE_ROOK(WHITE, ROOK, Set.of(Coordinate.create('a', 1), Coordinate.create('h', 1))),
    WHITE_KNIGHT(WHITE, KNIGHT, Set.of(Coordinate.create('b', 1), Coordinate.create('g', 1))),
    WHITE_BISHOP(WHITE, BISHOP, Set.of(Coordinate.create('c', 1), Coordinate.create('f', 1))),
    WHITE_KING(WHITE, KING, Set.of(Coordinate.create('e', 1))),
    WHITE_QUEEN(WHITE, QUEEN, Set.of(Coordinate.create('d', 1))),
    WHITE_PAWN(WHITE, PAWN, IntStream.range(0, 8).mapToObj(i -> Coordinate.create((char) ('a' + i), 2)).collect(Collectors.toUnmodifiableSet())),

    BLACK_ROOK(BLACK, ROOK, Set.of(Coordinate.create('a', 8), Coordinate.create('h', 8))),
    BLACK_KNIGHT(BLACK, KNIGHT, Set.of(Coordinate.create('b', 8), Coordinate.create('g', 8))),
    BLACK_BISHOP(BLACK, BISHOP, Set.of(Coordinate.create('c', 8), Coordinate.create('f', 8))),
    BLACK_KING(BLACK, KING, Set.of(Coordinate.create('e', 8))),
    BLACK_QUEEN(BLACK, QUEEN, Set.of(Coordinate.create('d', 8))),
    BLACK_PAWN(BLACK, PAWN, IntStream.range(0, 8).mapToObj(i -> Coordinate.create((char) ('a' + i), 7)).collect(Collectors.toUnmodifiableSet()));

    private static final Map<Color, Map<Figurine, Piece>> CLASSIFIED_PIECES = Arrays.stream(values())
            .collect(groupingBy(Piece::getColor, toUnmodifiableMap(Piece::getFigurine, Function.identity())));
    private final Color color;
    private final Figurine figurine;
    private final Set<Coordinate> initialCoordinates;

    public char getCode() {
        return color == WHITE
                ? Character.toUpperCase(figurine.getCode())
                : Character.toLowerCase(figurine.getCode());
    }

    public boolean legalMove(Board board, Coordinate from, Coordinate to) {
        if (sameColorFromTo(board, from, to) ||
                from.equals(to)) return false;

        List<Coordinate> coordinates = walk(board, from, to);

        if (coordinates.isEmpty()) return false;

        return coordinates.stream()
                .filter(coordinate -> !to.equals(coordinate))
                .map(board::findPiece)
                .noneMatch(Optional::isPresent);
    }

    private Boolean sameColorFromTo(Board board, Coordinate from, Coordinate to) {
        return board.findPiece(to)
                .map(piece -> piece.getColor() == board.getPiece(from).getColor())
                .orElse(false);
    }

    public List<Coordinate> walk(Board board, Coordinate from, Coordinate to) {
        return figurine.getLegalMoveRules().stream()
                .filter(move -> move.isApplicable(board, from, to))
                .findFirst().stream()
                .flatMap(move -> move.walk(from, to).stream())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Piece getPiece(Figurine figurine, Color color) {
        return CLASSIFIED_PIECES.get(color).get(figurine);
    }

    @RequiredArgsConstructor
    public enum Color {
        WHITE('-'), BLACK('*');
        @Getter
        private final char code;

        public Color change() {
            return this == WHITE ? BLACK : WHITE;
        }
    }
}
