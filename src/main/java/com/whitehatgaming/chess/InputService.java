package com.whitehatgaming.chess;

import com.whitehatgaming.UserInput;
import com.whitehatgaming.UserInputFile;
import lombok.RequiredArgsConstructor;
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
public class InputService {

    List<Move> getMoves(String path) {

        return newUserInputFile(path).stream()
                .map(move -> Move.of(
                        Coordinate.fromZeroIndexReversedColumn(move[0], move[1]),
                        Coordinate.fromZeroIndexReversedColumn(move[2], move[3])))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<String> getPath(String file) {
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

    @RequiredArgsConstructor(staticName = "create")
    private static class UserInputIterable implements SequentialStreamIterator<int[]> {
        private final UserInput userInput;
        private int[] nextMove;

        @Override
        public boolean hasNext() {
            try {
                nextMove = userInput.nextMove();
            } catch (IOException e) {
                log.warn("operation=errorReadingInput", e);
                return false;
            }
            return Objects.nonNull(nextMove);
        }

        @Override
        public int[] next() {
            return nextMove;
        }
    }
}
