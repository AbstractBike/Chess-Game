package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.game.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NotSameFromToTest {
    private final NotSameFromTo notSameFromTo = new NotSameFromTo();

    @Mock
    State state;

    @Test
    void assertLegal() {
        Move b2c3 = Move.valueOf("b2c3");

        assertThat(notSameFromTo.assertLegal(state, b2c3)).isEmpty();
    }

    @Test
    void assertIllegal() {
        Move b2c3 = Move.valueOf("b2b2");
        assertThat(notSameFromTo.assertLegal(state, b2c3))
                .containsInstanceOf(NotMoveException.class);
    }
}