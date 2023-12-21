package org.samasama.chess.piece;

import org.samasama.chess.match.Match;

import java.util.Objects;
import java.util.Optional;

public record Pawn(Color color) implements Piece {

    @Override
    public int value() {
        return 1;
    }

    public boolean validate(Move move, Match match) {
        Pos from = move.from();
        Pos to = move.to();

        // this will be moved to move function in Match because all pieces share them same errors
        if (from.rank() == to.rank()) return false;
        int direction = switch (color) {
            case BLACK -> 1;
            case WHITE -> -1;
        };
        if (from.file() == to.file()) {
            if (match.getBoard().getPiece(to).isPresent()) return false;
            boolean isStartingPoint = switch (color) {
                case BLACK -> from.rank() == 1;
                case WHITE -> from.rank() == 6;
            };
            if (isStartingPoint && to.rank() == from.rank() + 2 * direction) return true;
            return to.rank() == from.rank() + direction;
        }
        Optional<Piece> opposite = match.getBoard().getPiece(to);
        boolean upRight = Pos.of(from.file() - 1, from.rank() + direction).equals(to);
        boolean upLeft = Pos.of(from.file() + 1, from.rank() + direction).equals(to);
        if (upLeft || upRight) {
            if (opposite.isEmpty() && !match.getMovesHistory().isEmpty()) {
                Move lastMove = match.getMovesHistory().getLast();
                if (lastMove == null) {
                    return false;
                }
                if (!(lastMove.piece() instanceof Pawn)) {
                    return false;
                }
                return Math.abs(lastMove.from().rank() - lastMove.to().rank()) == 2;
            }
            // this will be moved to move function in Match because all pieces share them same errors
            if (opposite.isPresent()) {
                return !(opposite.get().color() == color);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Move move, Match match) {
        return validate(move, match);
    }

    @Override
    public String toString() {
        if (color == Color.WHITE) return "p";
        return "P";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Pawn) obj;
        return Objects.equals(this.color, that.color);
    }

}
