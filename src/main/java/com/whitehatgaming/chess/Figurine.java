package com.whitehatgaming.chess;

import com.whitehatgaming.chess.moverules.CapturingOneStepForwardDiagonal;
import com.whitehatgaming.chess.moverules.Diagonal;
import com.whitehatgaming.chess.moverules.Horizontal;
import com.whitehatgaming.chess.moverules.L;
import com.whitehatgaming.chess.moverules.MoveRule;
import com.whitehatgaming.chess.moverules.OneStep;
import com.whitehatgaming.chess.moverules.OneStepForward;
import com.whitehatgaming.chess.moverules.TwoStepForwardInitialState;
import com.whitehatgaming.chess.moverules.Vertical;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Figurine {
    ROOK('R', Set.of(Vertical.INSTANCE, Horizontal.INSTANCE)),
    KNIGHT('N', Set.of(L.INSTANCE)),
    BISHOP('B', Set.of(Diagonal.INSTANCE)),
    KING('K', Set.of(OneStep.INSTANCE)),
    QUEEN('Q', Set.of(Vertical.INSTANCE, Horizontal.INSTANCE, Diagonal.INSTANCE)),
    PAWN('P', Set.of(OneStepForward.INSTANCE, TwoStepForwardInitialState.INSTANCE, CapturingOneStepForwardDiagonal.INSTANCE));

    private final char code;
    private final Set<MoveRule> legalMoveRules;
}