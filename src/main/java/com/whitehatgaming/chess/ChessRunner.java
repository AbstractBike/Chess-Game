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
        if (args.length != 1) {
            log.info("usage: chessapp moves_file_path");
            exitCode = 1;
        } else {
            String filename = args[0];
            exitCode = inputService.getPath(filename)
                    .map(this::playGame)
                    .orElseGet(() -> {
                        log.error("couldn't load the file: " + filename);
                        return 2;
                    });
        }

    }

    private int playGame(String arg) {
        List<Move> moves = inputService.getMoves(arg);

        Game game = gameService.newGame();

        return moves.stream()
                .map(game::move)
                .peek(result -> {
                    result.peek(state -> log.info(state.toString()));
                    result.peekLeft(errors -> log.error(new ChessException(errors).getMessage()));
                })
                .collect(toUnmodifiableList())
                .stream()
                .allMatch(Either::isRight) ? 0 : 3;
    }
}
