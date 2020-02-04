package com.whitehatgaming.chess.game;

import com.whitehatgaming.chess.assertions.PostAssertionService;
import com.whitehatgaming.chess.assertions.PreAssertionService;
import com.whitehatgaming.chess.check.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameFactory {
    private final PreAssertionService preAssertionService;
    private final PostAssertionService postAssertionService;
    private final CheckService checkService;

    public Game newGame() {
        return Game.builder()
                .preAssertionService(preAssertionService)
                .postAssertionService(postAssertionService)
                .checkService(checkService)
                .build();
    }
}
