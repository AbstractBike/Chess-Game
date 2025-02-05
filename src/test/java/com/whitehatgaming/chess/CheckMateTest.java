package com.whitehatgaming.chess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import static com.whitehatgaming.chess.ExitCode.SUCCESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ComponentScan
@SpringBootTest(args = "checkmate.txt")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class CheckMateTest {

    @Autowired
    ChessRunner chessRunner;

    @Test
    void run() {
        assertThat(chessRunner.getExitCode()).isEqualTo(SUCCESS.getCode());
    }
}