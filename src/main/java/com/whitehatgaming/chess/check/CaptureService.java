package com.whitehatgaming.chess.check;

import com.whitehatgaming.chess.Figurine;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Coordinate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CaptureService {

    public boolean canCapture(Board board, Piece.Color color, Coordinate coordinate) {
        return board.getPieces(color).entrySet().stream()
                .anyMatch(pieceEntry -> canCapture(board, pieceEntry.getKey(), pieceEntry.getValue(), coordinate));
    }

    boolean canCapture(Board board, Coordinate from, Coordinate to) {
        return board.findPiece(from)
                .filter(piece -> piece.legalMove(board, from, to))
                .isPresent();
    }

    boolean canCaptureByBlockable(Board board, Piece.Color colorBlockableFigures, Coordinate kingCoordinate) {
        return Figurine.getBlockables().stream()
                .map(figurine -> Piece.getPiece(figurine, colorBlockableFigures))
                .anyMatch(piece -> board.getCoordinates(piece).stream()
                        .anyMatch(from -> piece.legalMove(board, from, kingCoordinate)));
    }

    private boolean canCapture(Board board, Piece piece, Set<Coordinate> coordinates, Coordinate to) {
        return coordinates.stream()
                .anyMatch(from -> piece.legalMove(board, from, to));
    }
}
