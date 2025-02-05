package com.whitehatgaming.chess;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.check.CaptureService;
import com.whitehatgaming.chess.check.CheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CheckServiceTest {
    @Spy
    CaptureService captureService;
    @InjectMocks
    CheckService checkService;

    @Test
    void isKingInCheckPosition() {
        assertThat(checkService.isKingInCheckPosition(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")
                .move("d7d6")
                .move("f3f7")
                .move("e8e7"), true))
                .isTrue();
    }

    @Test
    void isNotKingInCheckPosition() {
        assertThat(checkService.isKingInCheckPosition(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3"), false))
                .isFalse();
    }

    @Test
    void isCheck() {
        assertThat(checkService.isCheck(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")
                .move("d7d6")
                .move("f3f7")))
                .isTrue();
    }

    @Test
    void isNotCheck() {
        assertThat(checkService.isCheck(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")))
                .isFalse();
    }

    @Test
    public void isNotCheckMate() {
        assertThat(checkService.isCheckMate(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")
                .move("d7d5")
                .move("f3f7")))
                .isFalse();
    }

    public void isCheckMate() {
        assertThat(checkService.isCheckMate(Board.initialState()
                .move("e2e4")
                .move("e7e5")
                .move("f1c4")
                .move("b8c6")
                .move("d1f3")
                .move("d7d6")
                .move("f3f7")))
                .isFalse();
    }

}