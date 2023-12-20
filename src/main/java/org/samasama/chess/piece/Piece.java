package org.samasama.chess.piece;

import org.samasama.chess.match.Match;

public sealed interface Piece permits Pawn, Rock {
    Piece BLACK_PAWN = new Pawn(Color.BLACK);
    Piece WHITE_PAWN = new Pawn(Color.WHITE);

    public Color color();

    public int value();

    public boolean apply(Position from, Position to, Match match);

    public boolean validate(Position from, Position to, Match match);

    public String toString();

}
