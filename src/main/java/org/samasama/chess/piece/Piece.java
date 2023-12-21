package org.samasama.chess.piece;

import org.samasama.chess.match.Match;

public sealed interface Piece permits Pawn, Rock {
    Piece BLACK_PAWN = new Pawn(Color.BLACK);
    Piece WHITE_PAWN = new Pawn(Color.WHITE);
    Piece BLACK_ROCK = new Rock(Color.BLACK);
    Piece WHITE_ROCK = new Rock(Color.WHITE);

    public Color color();

    public int value();

    public boolean apply(Move move, Match match);

    public boolean validate(Move move, Match match);

    public String toString();

}
