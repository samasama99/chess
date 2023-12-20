package org.samasama.chess.piece;

import org.junit.jupiter.api.Test;
import org.samasama.chess.board.Board;
import org.samasama.chess.match.Match;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.samasama.chess.piece.Piece.BLACK_PAWN;
import static org.samasama.chess.piece.Piece.WHITE_PAWN;

class PawnTest {

    @Test
    void validateMoveFirstMoveTwoStepsAndOneStep() {
        Piece blackPawn = BLACK_PAWN;
        Piece whitePawn = WHITE_PAWN;
        Match match = mock(Match.class);

        Board board = new Board();
        when(match.getBoard()).thenReturn(board);

        assertTrue(blackPawn.validate(
                Position.create(0, 1),
                Position.create(0, 3),
                match
        ));

        assertTrue(whitePawn.validate(
                Position.create(1, 6),
                Position.create(1, 4),
                match
        ));

        assertTrue(blackPawn.validate(
                Position.create(2, 1),
                Position.create(2, 2),
                match
        ));

        assertTrue(whitePawn.validate(
                Position.create(3, 6),
                Position.create(3, 5),
                match
        ));
    }

    @Test
    void validateMovedOneStep() {
        Match match = mock(Match.class);

        Board board = new Board();
        when(match.getBoard()).thenReturn(board);

        assertTrue(BLACK_PAWN.validate(
                Position.create(5, 2),
                Position.create(5, 3),
                match
        ));
        assertTrue(WHITE_PAWN.validate(
                Position.create(7, 6),
                Position.create(7, 5),
                match
        ));
    }

    @Test
    void validateMoveInvalidSamePosition() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Position.create(2, 3));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move to the same position should be invalid
        assertFalse(blackPawn.validate(
                Position.create(2, 3),
                Position.create(2, 3),
                match
        ));
    }

    @Test
    void validateMoveInvalidBackwardMove() {
        Piece whitePawn = WHITE_PAWN;
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(whitePawn, Position.create(4, 5));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move backward should be invalid
        assertFalse(whitePawn.validate(
                Position.create(4, 4),
                Position.create(4, 5),
                match
        ));
    }

    @Test
    void validateMoveInvalidTwoStepsNotStartingPoint() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Position.create(3, 2));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move two steps without being at the starting point should be invalid
        assertFalse(blackPawn.validate(
                Position.create(3, 2),
                Position.create(3, 4),
                match
        ));
    }

    @Test
    void validateMoveInvalidCaptureOwnPiece() {
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(WHITE_PAWN, Position.create(5, 6));
        board.putPiece(WHITE_PAWN, Position.create(4, 5));
        when(match.getBoard()).thenReturn(board);

        // Attempting to capture own piece should be invalid
        assertFalse(WHITE_PAWN.validate(
                Position.create(5, 6),
                Position.create(4, 5),
                match
        ));
    }

    @Test
    void validateMoveValidCaptureOpponentPiece() {
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(BLACK_PAWN, Position.create(6, 3));
        board.putPiece(WHITE_PAWN, Position.create(7, 4));
        when(match.getBoard()).thenReturn(board);

        // Valid move to capture opponent's piece
        assertTrue(BLACK_PAWN.validate(
                Position.create(6, 3),
                Position.create(7, 4),
                match
        ));
    }

    @Test
    void validateEnPassantWhite() {
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(BLACK_PAWN, Position.create(4, 3));
        board.putPiece(WHITE_PAWN, Position.create(3, 3));
        System.out.println(board);
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                new Move(BLACK_PAWN, Position.create(3, 1), Position.create(3, 3))
                        )
                );

        assertTrue(WHITE_PAWN.validate(
                Position.create(4, 3),
                Position.create(3, 2),
                match
        ));
    }

    @Test
    void validateEnPassantBlack() {
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(WHITE_PAWN, Position.create(4, 1));
        board.putPiece(BLACK_PAWN, Position.create(6, 2));
        System.out.println(board);
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                new Move(WHITE_PAWN, Position.create(6, 1), Position.create(6, 3))
                        )
                );

        assertTrue(BLACK_PAWN.validate(
                Position.create(4, 1),
                Position.create(5, 2),
                match
        ));
    }

    @Test
    void validateEnPassantInvalidNoDoubleMove() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(blackPawn, Position.create(4, 4));
        board.putPiece(WHITE_PAWN, Position.create(6, 3));
        System.out.println(board);
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                new Move(blackPawn, Position.create(4, 6), Position.create(4, 4))
                                // No double move by the opponent's pawn
                        )
                );

        // Attempting en passant without the opponent's pawn making a double move should be invalid
        assertFalse(blackPawn.validate(
                Position.create(4, 4),
                Position.create(4, 2),
                match
        ));
    }

    @Test
    void validateEnPassantInvalidNoAdjacentPawn() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(blackPawn, Position.create(4, 4));
        board.putPiece(WHITE_PAWN, Position.create(6, 4));
        System.out.println(board);
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                new Move(blackPawn, Position.create(4, 6), Position.create(4, 4))
                        )
                );

        assertFalse(blackPawn.validate(
                Position.create(4, 4),
                Position.create(4, 2),
                match
        ));
    }

}