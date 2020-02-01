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

import static com.whitehatgaming.chess.ExitCode.ARGUMENT_ERROR;
import static com.whitehatgaming.chess.ExitCode.FILE_ERROR;
import static com.whitehatgaming.chess.ExitCode.ILLEGAL_MOVE_ERROR;
import static com.whitehatgaming.chess.ExitCode.SUCCESS;
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
        exitCode = call(args);
    }

    private int call(String[] args) {
        if (args.length != 1) {
            log.info("usage: chessapp moves_file_path");
            return ARGUMENT_ERROR.getCode();
        }

        return loadFileAndPlay(args[0]);
    }

    private int loadFileAndPlay(String filename) {
        return inputService.getPath(filename)
                .map(inputService::getMoves)
                .map(this::playGame)
                .orElseGet(() -> {
                    log.error("couldn't load the file: " + filename);
                    return FILE_ERROR.getCode();
                });
    }


    private int playGame(List<Move> moves) {

        Game game = gameService.newGame();

        return moves.stream()
                .map(game::move)
                .peek(result -> {
                    result.peek(state -> log.info(state.toString()));
                    result.peekLeft(errors -> log.error(new ChessException(errors).getMessage()));
                })
                .collect(toUnmodifiableList())
                .stream()
                .allMatch(Either::isRight) ? SUCCESS.getCode() : ILLEGAL_MOVE_ERROR.getCode();
    }
}
