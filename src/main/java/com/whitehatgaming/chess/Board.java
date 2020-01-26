package com.whitehatgaming.chess;

import com.google.common.collect.Maps;
import com.whitehatgaming.chess.Piece.Color;
import com.whitehatgaming.chess.assertions.PieceNotFoundException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.whitehatgaming.chess.Piece.Color.BLACK;
import static com.whitehatgaming.chess.Piece.Color.WHITE;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class Board {
    static final int SIZE = 8;

    private static final Board INITIAL_STATE = new Board(
            Color.WHITE,
            Arrays.stream(Piece.values())
                    .collect(toMap(Function.identity(), Piece::getInitialCoordinates, Streams::throwingMerger, () -> Maps.newEnumMap(Piece.class))),
            Arrays.stream(Piece.values())
                    .collect(() -> new Piece[SIZE][SIZE],
                            (board, piece) -> piece.getInitialCoordinates()
                                    .forEach(coordinate -> board[coordinate.getZeroIndexColumn()][coordinate.getZeroIndexRow()] = piece),
                            Streams::throwingMerger));
    @Getter
    private final Color nextTurn;
    private final Map<Piece, Set<Coordinate>> pieces;

    private final Piece[][] board;

    private Board(Color nextTurn, Map<Piece, Set<Coordinate>> pieces, Piece[][] state) {
        this.nextTurn = nextTurn;
        this.pieces = pieces;
        board = state;
    }

    public static Board initialState() {
        return INITIAL_STATE;
    }

    public Optional<Piece> findPiece(Coordinate c) {
        return Optional.ofNullable(board[c.getZeroIndexColumn()][c.getZeroIndexRow()]);
    }

    public Piece getPiece(Coordinate c) {
        return findPiece(c).orElseThrow(() -> new PieceNotFoundException(c));
    }

    public Set<Coordinate> getCoordinates(Piece piece) {
        return Optional.ofNullable(pieces.get(piece)).orElse(Collections.emptySet());
    }

    public Board move(String move) {
        return move(Move.valueOf(move));
    }

    private Board move(Coordinate from, Coordinate to) {
        return new Board(nextTurn.change(), Collections.unmodifiableMap(movePieces(from, to)), moveArray(from, to));
    }

    public Board move(Move move) {
        return move(move.getFrom(), move.getTo());
    }

    public Map<Piece, Set<Coordinate>> getPieces(Color color) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getKey().getColor().equals(color))
                .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Piece, Set<Coordinate>> movePieces(Coordinate from, Coordinate to) {

        Map<Piece, Set<Coordinate>> newPieces = Maps.newEnumMap(pieces);

        findPiece(to).ifPresent(capturedPiece -> remove(newPieces, capturedPiece, to));
        move(newPieces, getPiece(from), from, to);

        return newPieces;
    }

    private Piece[][] moveArray(Coordinate from, Coordinate to) {

        Piece[][] newState = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);

        newState[to.getZeroIndexColumn()][to.getZeroIndexRow()] = newState[from.getZeroIndexColumn()][from.getZeroIndexRow()];
        newState[from.getZeroIndexColumn()][from.getZeroIndexRow()] = null;

        return newState;
    }

    private void move(Map<Piece, Set<Coordinate>> pieces, Piece piece, Coordinate from, Coordinate to) {

        pieces.compute(piece, (p, coordinates) -> Stream.concat(
                Stream.of(to),
                filter(requireNonNull(coordinates), from))
                .collect(toUnmodifiableSet()));
    }

    private void remove(Map<Piece, Set<Coordinate>> pieces, Piece capturedPiece, Coordinate to) {

        Set<Coordinate> coordinates = filter(pieces.get(capturedPiece), to).collect(toUnmodifiableSet());

        if (coordinates.isEmpty()) {
            pieces.remove(capturedPiece);
        } else {
            pieces.replace(capturedPiece, coordinates);
        }
    }

    private Stream<Coordinate> filter(Set<Coordinate> coordinates, Coordinate coordinate) {
        return coordinates.stream().filter(c -> !coordinate.equals(c));
    }

    @Override
    public String toString() {
        return IntStreams.rangeClosed(SIZE - 1, 0)
                .mapToObj(r -> IntStream.range(0, SIZE)
                        .mapToObj(c -> Optional.ofNullable(board[c][r])
                                .map(Piece::getCode)
                                .orElseGet(() -> (c + r) % 2 == 0 ? BLACK.getCode() : WHITE.getCode()))
                        .map(String::valueOf)
                        .collect(joining("\t")))
                .collect(joining("\n"));
    }
}
