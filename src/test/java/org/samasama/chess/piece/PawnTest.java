package org.samasama.chess.piece;

import org.junit.jupiter.api.Test;
import org.samasama.chess.board.Board;
import org.samasama.chess.board.Match;
import org.samasama.chess.board.Type;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PawnTest {

    @Test
    void validateMoveFirstMoveTwoStepsAndOneStep() {
        Piece blackPawn = PieceFactory.of(Type.PAWN, Color.BLACK);
        Piece whitePawn = PieceFactory.of(Type.PAWN, Color.WHITE);
        Match match = mock(Match.class);

        Board board = new Board();
        when(match.getBoard()).thenReturn(board);

        // Test black pawn's initial two-step move
        assertTrue(blackPawn.validateMove(
                Position.create(0, 1),
                Position.create(0, 3),
                match
        ));

        // Test white pawn's subsequent one-step move
        assertTrue(whitePawn.validateMove(
                Position.create(1, 7),
                Position.create(1, 5),
                match
        ));

        // Test black pawn's subsequent one-step move
        assertTrue(blackPawn.validateMove(
                Position.create(2, 1),
                Position.create(2, 2),
                match
        ));
        // Test white pawn's subsequent one-step move
        assertTrue(whitePawn.validateMove(
                Position.create(3, 7),
                Position.create(3, 6),
                match
        ));
    }

    @Test
    void validateMovedOneStep() {
        Piece blackPawn = PieceFactory.of(Type.PAWN, Color.BLACK);
        Piece whitePawn = PieceFactory.of(Type.PAWN, Color.WHITE);
        Match match = mock(Match.class);

        Board board = new Board();
        when(match.getBoard()).thenReturn(board);

        assertTrue(blackPawn.validateMove(
                Position.create(5, 2),
                Position.create(5, 3),
                match
        ));
        assertTrue(whitePawn.validateMove(
                Position.create(7, 6),
                Position.create(7, 5),
                match
        ));
    }

    @Test
    void validateMoveInvalidSamePosition() {
        Piece blackPawn = PieceFactory.of(Type.PAWN, Color.BLACK);
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Position.create(2, 3));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move to the same position should be invalid
        assertFalse(blackPawn.validateMove(
                Position.create(2, 3),
                Position.create(2, 3),
                match
        ));
    }

    @Test
    void validateMoveInvalidBackwardMove() {
        Piece whitePawn = PieceFactory.of(Type.PAWN, Color.WHITE);
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(whitePawn, Position.create(4, 5));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move backward should be invalid
        assertFalse(whitePawn.validateMove(
                Position.create(4, 4),
                Position.create(4, 5),
                match
        ));
    }

    @Test
    void validateMoveInvalidTwoStepsNotStartingPoint() {
        Piece blackPawn = PieceFactory.of(Type.PAWN, Color.BLACK);
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Position.create(3, 2));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move two steps without being at the starting point should be invalid
        assertFalse(blackPawn.validateMove(
                Position.create(3, 2),
                Position.create(3, 4),
                match
        ));
    }

    @Test
    void validateMoveInvalidCaptureOwnPiece() {
        Piece whitePawn = PieceFactory.of(Type.PAWN, Color.WHITE);
        Piece anotherWhitePiece = PieceFactory.of(Type.PAWN, Color.WHITE);
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(whitePawn, Position.create(5, 6));
        board.putPiece(anotherWhitePiece, Position.create(4, 5));
        when(match.getBoard()).thenReturn(board);

        // Attempting to capture own piece should be invalid
        assertFalse(whitePawn.validateMove(
                Position.create(5, 6),
                Position.create(4, 5),
                match
        ));
    }

    @Test
    void validateMoveValidCaptureOpponentPiece() {
        Piece blackPawn = PieceFactory.of(Type.PAWN, Color.BLACK);
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Position.create(6, 3));
        board.putPiece(PieceFactory.of(Type.PAWN, Color.WHITE), Position.create(7, 4));
        when(match.getBoard()).thenReturn(board);

        // Valid move to capture opponent's piece
        assertTrue(blackPawn.validateMove(
                Position.create(6, 3),
                Position.create(7, 4),
                match
        ));
    }
}