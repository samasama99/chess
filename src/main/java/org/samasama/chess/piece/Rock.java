package org.samasama.chess.piece;

import org.samasama.chess.board.Board;
import org.samasama.chess.match.Match;

import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public record Rock(Color color) implements Piece {
    static boolean isPieceInTheWayInTheRank(Pos from, Pos to, Board board, int rank) {
        int a = from.rank();
        int b = to.rank();
        return IntStream.range(min(a, b) + 1, max(a, b))
                .mapToObj(n -> Pos.of(rank, n))
                .map(board::getPiece)
                .anyMatch(Optional::isPresent);
    }

    static boolean isPieceInTheWayInTheFile(Pos from, Pos to, Board board, int file) {
        int a = from.file();
        int b = to.file();
        return IntStream.range(min(a, b) + 1, max(a, b))
                .mapToObj(n -> Pos.of(n, file))
                .map(board::getPiece)
                .anyMatch(Optional::isPresent);
    }

    @Override
    public int value() {
        return 5;
    }

    @Override
    public boolean apply(Move move, Match match) {
        return validate(move, match);
    }

    @Override
    public boolean validate(Move move, Match match) {
        Pos from = move.from();
        Pos to = move.to();

        if (from.rank() == to.rank() && from.file() != to.file()) {
            if (Math.abs(from.file() - to.file()) == 1) return true;
            int rank = from.rank();
            return !isPieceInTheWayInTheFile(from, to, match.getBoard(), rank);
        }
        if (from.rank() != to.rank() && from.file() == to.file()) {
            if (Math.abs(from.rank() - to.rank()) == 1) return true;
            int file = from.file();
            return !isPieceInTheWayInTheRank(from, to, match.getBoard(), file);
        }
        return false;
    }

    @Override
    public String toString() {
        if (color == Color.WHITE) return "r";
        return "R";
    }
}