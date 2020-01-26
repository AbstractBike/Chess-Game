package com.whitehatgaming.chess.assertions;

class IllegalMoveException extends RuntimeException {
    IllegalMoveException(String message) {
        super(message);
    }
}
