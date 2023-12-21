package org.samasama.chess.piece;

import org.samasama.chess.service.ChessService;

public record Move(Piece piece,
                   Pos from,
                   Pos to,
                   Piece takenPiece,
                   Piece promotionPiece
) {
    public static Move from(ChessService.MoveDTO moveDTO) {
        return new Move(moveDTO.piece(), moveDTO.from(), moveDTO.to(), moveDTO.takenPiece(), moveDTO.promotionPiece());
    }

    public static Move from(Piece piece, Pos from, Pos to, Piece takenPiece, Piece promotionPiece) {
        return new Move(piece, from, to, takenPiece, promotionPiece);
    }

    public static Move from(Piece piece, Pos from, Pos to, Piece takenPiece) {
        return new Move(piece, from, to, takenPiece, null);
    }

    public static Move from(Piece piece, Pos from, Pos to) {
        return new Move(piece, from, to, null, null);
    }

}
