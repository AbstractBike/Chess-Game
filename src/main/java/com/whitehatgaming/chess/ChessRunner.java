package com.whitehatgaming.chess;

import com.whitehatgaming.chess.assertions.ChessException;
import com.whitehatgaming.chess.game.Game;
import com.whitehatgaming.chess.game.GameService;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChessRunner implements CommandLineRunner, ExitCodeGenerator {
    private final GameService gameService;
    private final InputService inputService;

    @Getter
    private int exitCode;

    @Override
    public void run(String... args) {

        List<Move> moves = inputService.getMoves(args[0]);

        Game game = gameService.newGame();

        exitCode = moves.stream()
                .map(game::move)
                .peek(result -> {
                    result.peek(state -> log.info(state.toString()));
                    result.peekLeft(errors -> log.error(new ChessException(errors).getMessage()));
                })
                .collect(toUnmodifiableList())
                .stream()
                .allMatch(Either::isRight) ? 0 : 1;
    }
}
