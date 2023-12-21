package org.samasama.chess.piece;

public record Pos(int file, int rank) {
    static public Pos of(int file, int rank) {
        return new Pos(file, rank);
    }
}
