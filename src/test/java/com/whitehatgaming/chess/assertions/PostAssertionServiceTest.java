package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Board;
import com.whitehatgaming.chess.CheckService;
import com.whitehatgaming.chess.Move;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostAssertionServiceTest {

    @Mock
    Board board;
    @Mock
    CheckService checkService;
    @InjectMocks
    PostAssertionService postAssertionService;

    @Test
    void assertLegal() {
        Move b2c3 = Move.valueOf("b2c3");

        when(checkService.isKingInCheckPosition(board, false, b2c3.getTo())).thenReturn(false);

        assertThat(postAssertionService.assertLegal(board, false, b2c3.getTo())).isEmpty();
    }

    @Test
    void assertIllegal() {
        Move b2c3 = Move.valueOf("b2c3");

        when(checkService.isKingInCheckPosition(board, false, b2c3.getTo())).thenReturn(true);

        assertThat(postAssertionService.assertLegal(board, false, b2c3.getTo()))
                .hasSize(1)
                .allMatch(e -> e instanceof IllegalMoveException);
    }
}