package org.samasama.chess.piece;

import org.graalvm.collections.Pair;
import org.samasama.chess.board.Type;

import java.util.HashMap;
import java.util.Map;

public class PieceFactory {
    static final Map<Pair<Type, Color>, Piece> pieces = new HashMap<>();

    static {
        pieces.put(
                Pair.create(Type.PAWN, Color.WHITE), new Pawn(Color.WHITE)
        );
        pieces.put(
                Pair.create(Type.PAWN, Color.BLACK), new Pawn(Color.BLACK)
        );
    }

    static public Piece of(Type type, Color color) {
        return pieces.get(Pair.create(type, color));
    }

    static final public Piece BLACK_PAWN = of(Type.PAWN, Color.BLACK);
    static final public Piece WHITE_PAWN = of(Type.PAWN, Color.WHITE);
}
