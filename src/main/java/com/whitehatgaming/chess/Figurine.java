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

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Figurine {
    ROOK('R', true, Set.of(Vertical.INSTANCE, Horizontal.INSTANCE)),
    KNIGHT('N', false, Set.of(L.INSTANCE)),
    BISHOP('B', true, Set.of(Diagonal.INSTANCE)),
    KING('K', false, Set.of(OneStep.INSTANCE)),
    QUEEN('Q', true, Set.of(Vertical.INSTANCE, Horizontal.INSTANCE, Diagonal.INSTANCE)),
    PAWN('P', false, Set.of(OneStepForward.INSTANCE, TwoStepForwardInitialState.INSTANCE, CapturingOneStepForwardDiagonal.INSTANCE));

    @Getter
    private final static Set<Figurine> blockables = Arrays.stream(values()).filter(Figurine::isBlockable).collect(Collectors.toUnmodifiableSet());

    private final char code;
    private final boolean blockable;
    private final Set<MoveRule> legalMoveRules;

}