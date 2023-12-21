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
                Move.from(BLACK_PAWN, Pos.of(0, 1), Pos.of(0, 3)),
                match
        ));

        assertTrue(whitePawn.validate(
                Move.from(WHITE_PAWN, Pos.of(1, 6), Pos.of(1, 4)),
                match
        ));

        assertTrue(blackPawn.validate(
                Move.from(BLACK_PAWN, Pos.of(2, 1), Pos.of(2, 2)),
                match
        ));

        assertTrue(whitePawn.validate(
                Move.from(WHITE_PAWN, Pos.of(3, 6), Pos.of(3, 5)),
                match
        ));
    }

    @Test
    void validateMovedOneStep() {
        Match match = mock(Match.class);

        Board board = new Board();
        when(match.getBoard()).thenReturn(board);

        assertTrue(BLACK_PAWN.validate(
                Move.from(BLACK_PAWN, Pos.of(5, 2), Pos.of(5, 3)),
                match
        ));
        assertTrue(WHITE_PAWN.validate(
                Move.from(WHITE_PAWN, Pos.of(7, 6), Pos.of(7, 5)),
                match
        ));
    }

    @Test
    void validateMoveInvalidSamePosition() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Pos.of(2, 3));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move to the same position should be invalid
        assertFalse(blackPawn.validate(
                Move.from(BLACK_PAWN, Pos.of(2, 3), Pos.of(2, 3)),
                match
        ));
    }

    @Test
    void validateMoveInvalidBackwardMove() {
        Piece whitePawn = WHITE_PAWN;
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(whitePawn, Pos.of(4, 5));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move backward should be invalid
        assertFalse(whitePawn.validate(
                Move.from(WHITE_PAWN, Pos.of(4, 4), Pos.of(4, 5)),
                match
        ));
    }

    @Test
    void validateMoveInvalidTwoStepsNotStartingPoint() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(blackPawn, Pos.of(3, 2));
        when(match.getBoard()).thenReturn(board);

        // Attempting to move two steps without being at the starting point should be invalid
        assertFalse(blackPawn.validate(
                Move.from(BLACK_PAWN, Pos.of(3, 2), Pos.of(3, 4)),
                match
        ));
    }

    @Test
    void validateMoveInvalidCaptureOwnPiece() {
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(WHITE_PAWN, Pos.of(5, 6));
        board.putPiece(WHITE_PAWN, Pos.of(4, 5));
        when(match.getBoard()).thenReturn(board);

        // Attempting to capture own piece should be invalid
        assertFalse(WHITE_PAWN.validate(
                Move.from(WHITE_PAWN, Pos.of(5, 6), Pos.of(4, 5)),
                match
        ));
    }

    @Test
    void validateMoveValidCaptureOpponentPiece() {
        Match match = mock(Match.class);
        Board board = new Board();
        board.putPiece(BLACK_PAWN, Pos.of(6, 3));
        board.putPiece(WHITE_PAWN, Pos.of(7, 4));
        when(match.getBoard()).thenReturn(board);

        // Valid move to capture opponent's piece
        assertTrue(BLACK_PAWN.validate(
                Move.from(BLACK_PAWN, Pos.of(6, 3), Pos.of(7, 4)),
                match
        ));
    }

    @Test
    void validateEnPassantWhite() {
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(BLACK_PAWN, Pos.of(4, 3));
        board.putPiece(WHITE_PAWN, Pos.of(3, 3));
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                Move.from(BLACK_PAWN, Pos.of(3, 1), Pos.of(3, 3))
                        )
                );

        assertTrue(WHITE_PAWN.validate(
                Move.from(WHITE_PAWN, Pos.of(4, 3), Pos.of(3, 2)),
                match
        ));
    }

    @Test
    void validateEnPassantBlack() {
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(WHITE_PAWN, Pos.of(4, 1));
        board.putPiece(BLACK_PAWN, Pos.of(6, 2));
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                Move.from(WHITE_PAWN, Pos.of(6, 1), Pos.of(6, 3))
                        )
                );

        assertTrue(BLACK_PAWN.validate(
                Move.from(BLACK_PAWN, Pos.of(4, 1), Pos.of(5, 2)),
                match
        ));
    }

    @Test
    void validateEnPassantInvalidNoDoubleMove() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(blackPawn, Pos.of(4, 4));
        board.putPiece(WHITE_PAWN, Pos.of(6, 3));
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                Move.from(blackPawn, Pos.of(4, 6), Pos.of(4, 4))
                        )
                );

        // Attempting en passant without the opponent's pawn making a double move should be invalid
        assertFalse(blackPawn.validate(
                Move.from(blackPawn, Pos.of(4, 4), Pos.of(4, 2)),
                match
        ));
    }

    @Test
    void validateEnPassantInvalidNoAdjacentPawn() {
        Piece blackPawn = BLACK_PAWN;
        Match match = mock(Match.class);

        Board board = new Board();
        board.putPiece(blackPawn, Pos.of(4, 4));
        board.putPiece(WHITE_PAWN, Pos.of(6, 4));
        when(match.getBoard()).thenReturn(board);
        when(match.getMovesHistory())
                .thenReturn(
                        List.of(
                                Move.from(blackPawn, Pos.of(4, 6), Pos.of(4, 4))
                        )
                );

        assertFalse(blackPawn.validate(
                Move.from(blackPawn, Pos.of(4, 4), Pos.of(4, 2)),
                match
        ));
    }
}
