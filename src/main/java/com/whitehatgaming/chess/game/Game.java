package com.whitehatgaming.chess.game;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.CheckService;
import com.whitehatgaming.chess.Coordinate;
import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.assertions.PostAssertionService;
import com.whitehatgaming.chess.assertions.PreAssertionService;
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
    private final List<State> history = Lists.newArrayList(State.create(Board.initialState(), false));

    public Either<List<RuntimeException>, State> move(Move move) {

        State lastState = getLastState();

        List<RuntimeException> preAssertionsResult = preAssertionService.assertLegal(lastState, move);
        if (!preAssertionsResult.isEmpty()) {
            return Either.left(preAssertionsResult);
        }

        Board board = lastState.getBoard().move(move);

        List<RuntimeException> posAssertionResult = postAssertionService.assertLegal(board, lastState.isCheck(), move.getTo());
        if (!posAssertionResult.isEmpty()) {
            return Either.left(posAssertionResult);
        }

        return Either.right(newState(board, move.getTo()));
    }

    private State newState(Board board, Coordinate to) {
        State newState = State.create(board, checkService.isCheck(board, to));

        history.add(newState);

        return newState;
    }

    private State getLastState() {
        return Iterables.getLast(history);
    }

}
