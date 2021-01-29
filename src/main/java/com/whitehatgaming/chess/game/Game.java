package com.whitehatgaming.chess.game;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whitehatgaming.chess.Piece;
import com.whitehatgaming.chess.assertions.PostAssertionService;
import com.whitehatgaming.chess.assertions.PreAssertionService;
import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.board.Move;
import com.whitehatgaming.chess.check.CheckService;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Game {
    private final PreAssertionService preAssertionService;
    private final PostAssertionService postAssertionService;
    private final CheckService checkService;
    @Getter
    private final List<State> history = Lists.newArrayList(State.initialState());

    public Either<List<RuntimeException>, State> move(Move move) {

        State lastState = getLastState();

        List<RuntimeException> preAssertionsResult = preAssertionService.assertLegal(lastState, move);
        if (!preAssertionsResult.isEmpty()) {
            return Either.left(preAssertionsResult);
        }

        Board board = getPiece(move, lastState)
                .move(lastState.getBoard(), move.getFrom(), move.getTo());

        List<RuntimeException> postAssertionResult = postAssertionService.assertLegal(board, lastState.isCheck());
        if (!postAssertionResult.isEmpty()) {
            return Either.left(postAssertionResult);
        }

        return Either.right(newState(board));
    }

    private Piece getPiece(Move move, State lastState) {
        return lastState.getBoard().getPiece(move.getFrom());
    }

    private State newState(Board board) {
        boolean check = checkService.isCheck(board);
        State newState = State.builder()
                .board(board)
                .check(check)
                .checkMate(check && checkService.isCheckMate(board))
                .build();

        history.add(newState);

        return newState;
    }

    private State getLastState() {
        return Iterables.getLast(history);
    }

}
