package org.samasama.chess.service;

import org.samasama.chess.board.Board;
import org.samasama.chess.match.MatchProgress;
import org.samasama.chess.match.Mode;
import org.samasama.chess.piece.Piece;
import org.samasama.chess.piece.Pos;

import java.util.UUID;

public interface ChessService {
    public GameStateDTO createGame(Mode mode);

    public GameStateDTO move(MoveDTO moveDTO) throws Exception;

    public record GameStateDTO(
            UUID uuid,
            Board board,
            MatchProgress matchProgress
    ) {
    }

    public record MoveDTO(
            UUID uuid,
            Piece piece,
            Pos from,
            Pos to,
            Piece takenPiece,
            Piece promotionPiece
    ) {
    }
}
//    GameStateDTO move(UUID gameId, Piece piece, Position from, Position to) throws Exception;
