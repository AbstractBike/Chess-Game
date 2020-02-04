package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.check.CheckService;
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

        when(checkService.isKingInCheckPosition(board, false)).thenReturn(false);

        assertThat(postAssertionService.assertLegal(board, false)).isEmpty();
    }

    @Test
    void assertIllegal() {

        when(checkService.isKingInCheckPosition(board, false)).thenReturn(true);

        assertThat(postAssertionService.assertLegal(board, false))
                .hasSize(1)
                .allMatch(e -> e instanceof IllegalMoveException);
    }
}