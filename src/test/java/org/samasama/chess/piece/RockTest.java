package org.samasama.chess.piece;

import org.junit.Test;
import org.samasama.chess.board.Board;
import org.samasama.chess.match.Match;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.samasama.chess.piece.Piece.*;
import static org.samasama.chess.piece.Rock.isPieceInTheWayInTheFile;
import static org.samasama.chess.piece.Rock.isPieceInTheWayInTheRank;

public class RockTest {
    @Test
    public void isPieceInTheWayInTheRank_NoPieceInBetween_ShouldReturnFalse() {
        Board board = new Board();
        Pos from = Pos.of(0, 0);
        Pos to = Pos.of(0, 3);

        boolean result = isPieceInTheWayInTheRank(from, to, board, 0);

        assertFalse(result);
    }

    @Test
    public void isPieceInTheWayInTheRank_PieceInBetween_ShouldReturnTrue() {
        Board board = new Board();
        board.putPiece(WHITE_PAWN, Pos.of(0, 2));
        Pos from = Pos.of(0, 0);
        Pos to = Pos.of(0, 3);

        assertTrue(isPieceInTheWayInTheRank(from, to, board, 0));
        assertTrue(isPieceInTheWayInTheRank(to, from, board, 0));
    }

    @Test
    public void isPieceInTheWayInTheFile_NoPieceInBetween_ShouldReturnFalse() {
        Board board = new Board();
        Pos from = Pos.of(0, 0);
        Pos to = Pos.of(3, 0);

        boolean result = isPieceInTheWayInTheFile(from, to, board, 0);

        assertFalse(result);
    }

    @Test
    public void isPieceInTheWayInTheFile_PieceInBetween_ShouldReturnTrue() {
        Board board = new Board();
        board.putPiece(WHITE_PAWN, Pos.of(4, 0));
        Pos from = Pos.of(0, 0);
        Pos to = Pos.of(5, 0);
        assertTrue(isPieceInTheWayInTheFile(from, to, board, 0));
        assertTrue(isPieceInTheWayInTheFile(to, from, board, 0));
    }

    @Test
    public void movingRockInRankWithoutTaking() {
        Board board = new Board();
        Pos from = Pos.of(4, 0);
        Pos to = Pos.of(4, 5);
        board.putPiece(BLACK_ROCK, from);
        Match match = mock(Match.class);
        when(match.getBoard()).thenReturn(board);
        assertTrue(BLACK_ROCK.validate(Move.from(BLACK_ROCK, from, to), match));
    }

    @Test
    public void movingRockInFileWithoutTaking() {
        Board board = new Board();
        Pos from = Pos.of(2, 5);
        Pos to = Pos.of(4, 5);
        board.putPiece(BLACK_ROCK, from);
        Match match = mock(Match.class);
        when(match.getBoard()).thenReturn(board);
        assertTrue(BLACK_ROCK.validate(Move.from(BLACK_ROCK, from, to), match));
    }

    @Test
    public void movingRockInRankWithTaking() {
        Board board = new Board();
        Pos from = Pos.of(4, 0);
        Pos to = Pos.of(4, 5);
        board.putPiece(BLACK_ROCK, from);
        board.putPiece(WHITE_PAWN, to);
        Match match = mock(Match.class);
        when(match.getBoard()).thenReturn(board);
        assertTrue(BLACK_ROCK.validate(Move.from(BLACK_ROCK, from, to), match));
    }

    @Test
    public void movingRockInFileWithTaking() {
        Board board = new Board();
        Pos from = Pos.of(2, 5);
        Pos to = Pos.of(4, 5);
        board.putPiece(BLACK_ROCK, from);
        board.putPiece(WHITE_PAWN, to);
        Match match = mock(Match.class);
        when(match.getBoard()).thenReturn(board);
        assertTrue(BLACK_ROCK.validate(Move.from(BLACK_ROCK, from, to), match));
    }

    @Test
    public void movingRockInRankWithTakingButThereIsAAPawnInTheWay() {
        Board board = new Board();
        Pos from = Pos.of(4, 0);
        Pos to = Pos.of(4, 5);
        board.putPiece(BLACK_ROCK, from);
        board.putPiece(WHITE_PAWN, to);
        board.putPiece(BLACK_PAWN, Pos.of(4, 3));
        System.out.println(board);
        Match match = mock(Match.class);
        when(match.getBoard()).thenReturn(board);
        assertFalse(BLACK_ROCK.validate(Move.from(BLACK_ROCK, from, to), match));
    }

    @Test
    public void movingRockInFileWithTakingButThereIsAAPawnInTheWay() {
        Board board = new Board();
        Pos from = Pos.of(2, 5);
        Pos to = Pos.of(4, 5);
        board.putPiece(BLACK_ROCK, from);
        board.putPiece(WHITE_PAWN, to);
        board.putPiece(WHITE_PAWN, Pos.of(3, 5));
        System.out.println(board);
        Match match = mock(Match.class);
        when(match.getBoard()).thenReturn(board);
        assertFalse(BLACK_ROCK.validate(Move.from(BLACK_ROCK, from, to), match));
    }
}