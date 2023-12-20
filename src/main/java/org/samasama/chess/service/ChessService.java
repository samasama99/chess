package org.samasama.chess.service;

import org.samasama.chess.board.Board;
import org.samasama.chess.match.Mode;
import org.samasama.chess.match.State;
import org.samasama.chess.match.Winner;
import org.samasama.chess.piece.Color;
import org.samasama.chess.piece.Piece;
import org.samasama.chess.piece.Position;

import java.util.UUID;

public interface ChessService {
    public GameStateDTO createGame(Mode mode);

    public GameStateDTO move(MoveDTO moveDTO) throws Exception;

    public record GameStateDTO(
            UUID uuid,
            Board board,
            State state,
            Color nextMove,
            Winner winner
    ) {
    }

    public record MoveDTO(
            UUID uuid,
            Piece piece,
            Position from,
            Position to
    ) {
    }
}
//    GameStateDTO move(UUID gameId, Piece piece, Position from, Position to) throws Exception;
