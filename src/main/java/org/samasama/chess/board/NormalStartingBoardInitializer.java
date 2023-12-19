package org.samasama.chess.board;

import org.samasama.chess.piece.Color;
import org.samasama.chess.piece.PieceFactory;
import org.samasama.chess.piece.Position;

public class NormalStartingBoardInitializer implements BoardInitializer {
    public Board board() {
        Board board = new Board();
        board.putPiece(
                PieceFactory.of(Type.PAWN, Color.WHITE),
                Position.create(0, 0)
        );
        return board;
    }

    @Override
    public InitialState initialState() {
        return InitialState.of(State.START, Color.WHITE, Winner.NO_RESULT, board());
    }

}
