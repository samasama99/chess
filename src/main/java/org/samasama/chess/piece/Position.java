package org.samasama.chess.piece;

public record Position(int file, int rank) {
    static public Position create(int file, int rank) {
        return new Position(file, rank);
    }
}
