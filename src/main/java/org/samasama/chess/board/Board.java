package org.samasama.chess.board;

import org.samasama.chess.piece.Piece;
import org.samasama.chess.piece.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    List<List<Optional<Piece>>> board;
    int blackPoints = 0;
    int whitePoints = 0;


    Board(List<List<Optional<Piece>>> board) {
        this.board = board;
    }

    public Board() {
        board = new ArrayList<>(8);
        IntStream.range(0, 8).forEach(i -> board.add(new ArrayList<>(8)));
        board.forEach(row -> IntStream.range(0, 8).forEach(i -> row.add(Optional.empty())));
    }

    public void putPiece(Piece piece, Position where) {
        var row = board.get(where.rank());
        Optional<Piece> p = board.get(where.rank()).get(where.file());
        int value = 0;
        if (p.isPresent()) {
            value = p.get().value();
        }
        row.set(where.file(), Optional.of(piece));

        switch (piece.color()) {
            case BLACK -> blackPoints += value;
            case WHITE -> whitePoints += value;
        }
    }

    public Optional<Piece> takePiece(Position from) {
        Optional<Piece> piece = board.get(from.rank()).get(from.file());
        board.get(from.rank()).set(from.file(), Optional.empty());
        return piece;
    }

    public Optional<Piece> getPiece(Position from) {
        return board.get(from.rank()).get(from.file());
    }

    @Override
    public String toString() {
        return board
                .stream()
                .map(Collection::stream)
                .map(row -> row.map(opt -> opt.map(Piece::toString).orElse(".")))
                .map(row -> row.collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
