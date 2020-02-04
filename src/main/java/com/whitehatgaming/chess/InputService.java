package com.whitehatgaming.chess;

import com.whitehatgaming.UserInput;
import com.whitehatgaming.UserInputFile;
import com.whitehatgaming.chess.board.Coordinate;
import com.whitehatgaming.chess.board.Move;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Slf4j
@Service
class InputService {

    List<Move> getMoves(String path) {

        return newUserInputFile(path).stream()
                .map(move -> Move.of(
                        Coordinate.fromZeroIndexReversedRow(move[0], move[1]),
                        Coordinate.fromZeroIndexReversedRow(move[2], move[3])))
                .collect(Collectors.toUnmodifiableList());
    }

    Optional<String> getPath(String file) {
        try {
            return Optional.of(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + file).getAbsolutePath());
        } catch (FileNotFoundException e) {
            try {
                return Optional.of(ResourceUtils.getFile(file))
                        .filter(File::exists)
                        .map(File::getAbsolutePath);
            } catch (FileNotFoundException ex) {
                return Optional.empty();
            }
        }
    }

    @SneakyThrows(FileNotFoundException.class)
    private SequentialStreamIterator<int[]> newUserInputFile(String file) {
        return UserInputIterable.create(new UserInputFile(file));
    }

    private static class UserInputIterable implements SequentialStreamIterator<int[]> {
        private final UserInput userInput;
        private int[] nextMove;

        private UserInputIterable(UserInput userInput) {
            this.userInput = userInput;
            nextMove = iterate();
        }

        static UserInputIterable create(UserInput userInput) {
            return new UserInputIterable(userInput);
        }

        @Override
        public boolean hasNext() {
            return Objects.nonNull(nextMove);
        }

        @Override
        public int[] next() {
            int[] next = nextMove;

            nextMove = iterate();

            return next;
        }

        private int[] iterate() {
            try {
                return userInput.nextMove();
            } catch (IOException e) {
                log.warn("operation=errorReadingInput", e);
                return null;
            }
        }
    }
}
