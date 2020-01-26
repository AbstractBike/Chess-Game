package com.whitehatgaming.chess.assertions;

import com.whitehatgaming.chess.Move;
import com.whitehatgaming.chess.game.State;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

@Service
public class PreAssertionService {
    private final List<PreAssertion> preAssertions;

    public PreAssertionService(List<PreAssertion> preAssertions) {
        this.preAssertions = preAssertions.stream().sorted(comparingInt(PreAssertion::getOrder)).collect(Collectors.toUnmodifiableList());
    }

    public List<RuntimeException> assertLegal(State lastState, Move move) {
        return preAssertions.stream()
                .map(preAssertion -> preAssertion.assertLegal(lastState, move))
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableList());
    }
}
