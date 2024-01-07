package org.samasama.chess.piece;

import org.samasama.chess.board.Board;
import org.samasama.chess.match.Match;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record King(Color color) implements Piece {
    @Override
    public int value() {
        return 0;
    }

    @Override
    public boolean apply(Move move, Match match) {
        return false;
    }

    @Override
    public boolean validate(Move move, Match match) {
        return false;
    }

    @Override
    public String toString() {
        if (color == Color.BLACK)
            return "r";
        return "R";
    }

    boolean isInCheck(Pos kingPosition, Match match) {
//        List<List<Optional<Piece>>> board = match.getBoard().getBoard();
//        if (color == Color.WHITE) {
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (board.get(i).get(j).isPresent()) {
//                        Piece piece = board.get(i).get(j).get();
//                        if (piece.color() == Color.BLACK) {
//                            if (piece.validate(Move.from(piece, Pos.of(i, j), kingPosition), match)) return true;
//                        }
//                    }
//                }
//            }
//        }
//        if (color == Color.BLACK) {
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (board.get(i).get(j).isPresent()) {
//                        Piece piece = board.get(i).get(j).get();
//                        if (piece.color() == Color.WHITE) {
//                            if (piece.validate(Move.from(piece, Pos.of(i, j), kingPosition), match)) return true;
//                        }
//                    }
//                }
//            }
//        }
        Board board = match.getBoard();
        System.out.println(IntStream.range(0, 8 * 8)
                .mapToObj(n -> Pos.of(n % 8, n / 8))
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                board::getPiece
                        )
                ).entrySet().stream().filter(e -> e.getValue().isPresent())
                .map(e -> Map.entry(e.getKey(), e.getValue().get()))
                .filter(e -> e.getValue().color() != color)
                .filter(e -> e.getValue().validate(Move.of(this, kingPosition, e.getValue()), match)).count());
//                .map(board::getPiece)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(piece -> piece.color() != color)
//                .filter(piece -> piece.validate())
//                .count());
////                .map(pos -> match.getBoard().getPiece(pos))
////                .filter(Optional::isPresent)
////                .map(Optional::get)
        return false;
    }

    public static void main(String[] args) {
        
    }
}
