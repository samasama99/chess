package org.samasama.chess.piece;

import org.junit.Test;
import org.samasama.chess.board.Board;
import org.samasama.chess.board.BoardAdapter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.samasama.chess.piece.Piece.WHITE_PAWN;
import static org.samasama.chess.piece.Rock.isPieceInTheWayInTheFile;
import static org.samasama.chess.piece.Rock.isPieceInTheWayInTheRank;

public class RockTest {
    @Test
    public void isPieceInTheWayInTheRank_NoPieceInBetween_ShouldReturnFalse() {
        Board board = new Board();
        Position from = Position.create(0, 0);
        Position to = Position.create(0, 3);

        boolean result = isPieceInTheWayInTheRank(from, to, board, 0);

        assertFalse(result);
    }

    @Test
    public void isPieceInTheWayInTheRank_PieceInBetween_ShouldReturnTrue() {
        Board board = new Board();
        board.putPiece(WHITE_PAWN, Position.create(0, 2));
        Position from = Position.create(0, 0);
        Position to = Position.create(0, 3);

        boolean result = isPieceInTheWayInTheRank(from, to, board, 0);

        assertTrue(result);
    }

    @Test
    public void isPieceInTheWayInTheFile_NoPieceInBetween_ShouldReturnFalse() {
        Board board = new Board();
        Position from = Position.create(0, 0);
        Position to = Position.create(3, 0);

        boolean result = isPieceInTheWayInTheFile(from, to, board, 0);

        assertFalse(result);
    }

    @Test
    public void isPieceInTheWayInTheFile_PieceInBetween_ShouldReturnTrue() {
        Board board = BoardAdapter
                .from("""
                        ...P....
                        ........
                        ........
                        ........
                        ........
                        ........
                        ........
                        ........
                        ........
                        """);
        System.out.println(board);
        board.putPiece(WHITE_PAWN, Position.create(2, 0));
        Position from = Position.create(0, 0);
        Position to = Position.create(5, 0);

        boolean result = isPieceInTheWayInTheFile(from, to, board, 0);

        assertTrue(result);
    }

}