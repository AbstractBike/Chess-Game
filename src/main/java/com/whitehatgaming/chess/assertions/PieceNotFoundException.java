package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Coordinate;

public class PieceNotFoundException extends RuntimeException {
    public PieceNotFoundException(Coordinate from) {
        super("Piece not found:" + from);
    }
}
