package com.whitehatgaming.chess.game;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.CheckService;
import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.assertions.PostAssertionService;
import com.whitehatgaming.chess.assertions.PreAssertionService;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.whitehatgaming.chess.Conditions.newCondition;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameTest {
    @Mock
    PreAssertionService preAssertionService;
    @Mock
    PostAssertionService postAssertionService;
    @Mock
    CheckService checkService;
    @InjectMocks
    Game game;

    @Test
    void move() {
        Move a2a4 = Move.valueOf("a2a4");

        when(preAssertionService.assertLegal(any(State.class), eq(a2a4)))
                .thenReturn(Collections.emptyList());
        when(postAssertionService.assertLegal(any(Board.class), eq(false), eq(a2a4.getTo()))).thenReturn(Collections.emptyList());
        when(checkService.isCheck(any(Board.class), eq(a2a4.getTo()))).thenReturn(false);

        assertThat(game.move(a2a4)).is(newCondition(Either::isRight));
    }

    @Test
    void noMove() {
        Move a2a4 = Move.valueOf("a2a2");

        when(preAssertionService.assertLegal(any(State.class), eq(a2a4)))
                .thenReturn(List.of(new RuntimeException()));

        assertThat(game.move(a2a4)).is(newCondition(Either::isLeft));
    }


    @Test
    void kingInCheck() {
        Move a2a4 = Move.valueOf("a2a4");

        when(preAssertionService.assertLegal(any(State.class), eq(a2a4)))
                .thenReturn(Collections.emptyList());
        when(postAssertionService.assertLegal(any(Board.class), eq(false), eq(a2a4.getTo())))
                .thenReturn(List.of(new RuntimeException()));

        assertThat(game.move(a2a4)).is(newCondition(Either::isLeft));
    }
}