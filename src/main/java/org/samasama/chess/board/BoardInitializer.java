package org.samasama.chess.board;

import org.samasama.chess.match.State;

/**
 * This interface, is used to fill the board with the appropriate init state,
 * Because the initial state could be different,
 * Because there could be different modes, like normal, puzzle, etc...
 */
public interface BoardInitializer {
    /**
     * State: START, ONGOING, ENDED
     * Next Move is COLOR
     * If the State is ENDED the COLOR is the winner, unless the color is GRAY
     */
    State initialState();

}
