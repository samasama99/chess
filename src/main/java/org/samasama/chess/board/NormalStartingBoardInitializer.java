package org.samasama.chess.board;

import org.samasama.chess.match.MatchProgress;
import org.samasama.chess.match.State;
import org.samasama.chess.piece.Piece;
import org.samasama.chess.piece.Pos;

public class NormalStartingBoardInitializer implements BoardInitializer {
    public Board board() {
        Board board = new Board();
        board.putPiece(
                Piece.WHITE_PAWN,
                Pos.of(0, 0)
        );
        return board;
    }

    @Override
    public State initialState() {
        return State.of(MatchProgress.START, board());
    }

}
