package org.samasama.chess.match;

import org.samasama.chess.board.Board;

public record State(MatchProgress matchProgress, Board board) {
    public static State of(MatchProgress matchProgress, Board board) {
        return new State(matchProgress, board);
    }
}
