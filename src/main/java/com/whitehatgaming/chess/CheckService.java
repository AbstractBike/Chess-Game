package com.whitehatgaming.chess;

import com.google.common.collect.Iterables;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.whitehatgaming.chess.Figurine.KING;

@Service
public class CheckService {

    public boolean isKingInCheckPosition(Board board, boolean wasKingInCheck, Coordinate to) {

        Piece.Color currentColor = board.getPiece(to).getColor();

        Coordinate kingCoordinate = getKingCoordinate(board, currentColor);

        return shouldAllPiecesBeTested(wasKingInCheck, to, kingCoordinate) && canCapture(board, currentColor.change(), kingCoordinate) ||
                canCaptureByBlockable(board, currentColor.change(), kingCoordinate);
    }

    private boolean shouldAllPiecesBeTested(boolean wasKingInCheck, Coordinate to, Coordinate kingCoordinate) {
        return wasKingInCheck || isKingMove(to, kingCoordinate);
    }


    public boolean isCheck(Board nextBoardState, Coordinate lastMove) {

        Piece.Color currentColor = nextBoardState.getPiece(lastMove).getColor();

        Coordinate kingCoordinates = Iterables.getOnlyElement(nextBoardState.getCoordinates(Piece.getPiece(KING, currentColor.change())));

        return canCapture(nextBoardState, lastMove, kingCoordinates)
                || canCaptureByBlockable(nextBoardState, currentColor, kingCoordinates);

    }

    private boolean canCapture(Board board, Piece.Color color, Coordinate kingCoordinate) {
        return board.getPieces(color).entrySet().stream()
                .anyMatch(pieceEntry -> canCapture(board, pieceEntry.getKey(), pieceEntry.getValue(), kingCoordinate));
    }

    private boolean canCapture(Board board, Piece piece, Set<Coordinate> coordinates, Coordinate to) {
        return coordinates.stream()
                .anyMatch(from -> piece.legalMove(board, from, to));
    }

    private boolean isKingMove(Coordinate to, Coordinate kingCoordinate) {
        return kingCoordinate.equals(to);
    }

    private boolean canCapture(Board board, Coordinate from, Coordinate to) {
        return board.findPiece(from)
                .filter(piece -> piece.legalMove(board, from, to))
                .isPresent();
    }

    private boolean canCaptureByBlockable(Board board, Piece.Color colorBlockableFigures, Coordinate kingCoordinate) {
        return Figurine.getBlockables().stream()
                .map(figurine -> Piece.getPiece(figurine, colorBlockableFigures))
                .anyMatch(piece -> board.getCoordinates(piece).stream()
                        .anyMatch(from -> piece.legalMove(board, from, kingCoordinate)));
    }

    private Coordinate getKingCoordinate(Board board, Piece.Color color) {
        return Iterables.getOnlyElement(board.getCoordinates(Piece.getPiece(KING, color)));
    }
}
