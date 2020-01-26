package com.whitehatgaming.chess;

import lombok.EqualsAndHashCode;

import java.util.Objects;

import static com.whitehatgaming.chess.Board.SIZE;

@EqualsAndHashCode
public class Coordinate {
    private final char x;
    private final int y;

    private Coordinate(char c, int y) {
        char x = Character.toLowerCase(c);
        Objects.checkIndex(y - 1, SIZE);
        Objects.checkIndex(x - 'a', SIZE);
        this.x = x;
        this.y = y;
    }

    public static Coordinate fromZeroIndex(int x, int y) {
        return Coordinate.create((char) ('a' + x), y + 1);
    }

    public static Coordinate fromZeroIndexReversedColumn(int x, int y) {
        return Coordinate.create((char) ('a' + x), SIZE - y);
    }

    public int getZeroIndexX() {
        return x - 'a';
    }

    public int getZeroIndexY() {
        return y - 1;
    }

    public static Coordinate create(char x, int y) {
        return new Coordinate(x, y);
    }

    public static Coordinate valueOf(String s) {
        assert s.length() == 2;
        return new Coordinate(s.charAt(0), Integer.parseInt(s.substring(1)));
    }

    @Override
    public String toString() {
        return String.format("%c%d", x, y);
    }

}
