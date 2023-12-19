package org.samasama.chess.piece;

import org.samasama.chess.board.Match;

public sealed interface Piece permits Pawn {
    public Color color();

    public int value();

    public boolean validateMove(Position from, Position to, Match match);

    public String toString();

}
