package org.samasama.chess.match;

import org.samasama.chess.board.NormalStartingBoardInitializer;

import java.util.ArrayList;
import java.util.Objects;

public class MatchFactory {
    static public Match create(Mode mode) {
        if (Objects.requireNonNull(mode) == Mode.NORMAL) {
            return new Match(new NormalStartingBoardInitializer(), new ArrayList<>());
        }
        throw new IllegalArgumentException();
    }
}
