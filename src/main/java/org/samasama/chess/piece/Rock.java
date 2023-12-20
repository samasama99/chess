package org.samasama.chess.piece;

import org.samasama.chess.board.Board;
import org.samasama.chess.match.Match;

import java.util.Optional;
import java.util.stream.IntStream;

public record Rock(Color color) implements Piece {
    static boolean isPieceInTheWayInTheRank(Position from, Position to, Board board, int rank) {
        return IntStream.range(from.file() + 1, to.file() - 1)
                .mapToObj(n -> board.getPiece(Position.create(rank, n)))
                .anyMatch(Optional::isPresent);
    }

    static boolean isPieceInTheWayInTheFile(Position from, Position to, Board board, int file) {
        return IntStream.range(from.file() + 1, to.file() - 1)
                .mapToObj(n -> board.getPiece(Position.create(n, file)))
                .anyMatch(Optional::isPresent);
    }

    @Override
    public int value() {
        return 5;
    }

    @Override
    public boolean apply(Position from, Position to, Match match) {
        return validate(from, to, match);
    }

    @Override
    public boolean validate(Position from, Position to, Match match) {
        if (from.rank() == to.rank() && from.file() != to.file()) {
            if (Math.abs(from.file() - to.file()) == 1) return true;
            int rank = from.rank();
            Board board = match.getBoard();

            if (from.file() > to.file()) {
                return !isPieceInTheWayInTheRank(from, to, board, rank);
            }
            return true;
        }
        if (from.rank() != to.rank() && from.file() == to.file()) {
            if (Math.abs(from.rank() - to.rank()) == 1) return true;
            int file = from.file();
            Board board = match.getBoard();

            if (from.rank() > to.rank()) {
                return !isPieceInTheWayInTheFile(from, to, board, file);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (color == Color.WHITE) return "r";
        return "R";
    }
}