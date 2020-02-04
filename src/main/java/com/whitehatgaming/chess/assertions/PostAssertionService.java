package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.board.Board;
import com.whitehatgaming.chess.check.CheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAssertionService {
    private final CheckService checkService;

    public List<RuntimeException> assertLegal(Board board, boolean wasKingInCheck) {

        if (checkService.isKingInCheckPosition(board, wasKingInCheck)) {
            return Collections.singletonList(new IllegalMoveException("King is in a check position"));
        }

        return Collections.emptyList();
    }
}
