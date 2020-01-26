package com.whitehatgaming.chess.game;

import com.whitehatgaming.chess.CheckService;
import com.whitehatgaming.chess.assertions.PostAssertionService;
import com.whitehatgaming.chess.assertions.PreAssertionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
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
