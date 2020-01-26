package com.whitehatgaming.chess.game;

import com.whitehatgaming.chess.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class State {
    private final Board board;
    private final boolean check;

    @Override
    public String toString() {
        return (check ? "CHECK" : "") + "\n" + board.toString();
    }
}