package org.samasama.chess.piece;

import org.samasama.chess.board.Match;

import java.util.Objects;
import java.util.Optional;

public record Pawn(Color color) implements Piece {

    @Override
    public int value() {
        return 1;
    }

    @Override
    public boolean validateMove(Position from, Position to, Match match) {
        if (from.rank() == to.rank()) return false;
        int direction = switch (color) {
            case BLACK -> 1;
            case WHITE -> -1;
        };
        if (from.file() == to.file()) {
            if (match.getBoard().getPiece(to).isPresent()) return false;
            boolean isStartingPoint = switch (color) {
                case BLACK -> from.rank() == 1;
                case WHITE -> from.rank() == 7;
            };
            if (isStartingPoint && to.rank() == from.rank() + 2 * direction) return true;
            return to.rank() == from.rank() + direction;
        }
        Optional<Piece> opposite = match.getBoard().getPiece(to);
        if (opposite.isEmpty()) return false;
        if (opposite.get().color() == color) return false;
        return (to.file() == from.file() + 1 || to.file() == from.file() - 1) && to.rank() == from.rank() + direction;
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
