package org.samasama.chess.piece;

import org.samasama.chess.service.ChessService;

public record Move(Piece piece, Position from, Position to) {
    public static Move from(ChessService.MoveDTO moveDTO) {
        return new Move(moveDTO.piece(), moveDTO.from(), moveDTO.to());
    }

    public static Move from(Piece piece, Position from, Position to) {
        return new Move(piece, from, to);
    }
}
