package org.samasama.chess.match;

import lombok.Getter;
import org.samasama.chess.board.Board;
import org.samasama.chess.board.BoardInitializer;
import org.samasama.chess.exceptions.IllegalMoveException;
import org.samasama.chess.exceptions.InvalidCaptureException;
import org.samasama.chess.exceptions.InvalidPositionException;
import org.samasama.chess.exceptions.PieceNotFoundException;
import org.samasama.chess.piece.Move;
import org.samasama.chess.piece.Piece;
import org.samasama.chess.piece.Pos;

import java.util.List;
import java.util.Optional;

/*
    TODO:
    = GENERATE CURRENT STATE =
    - Check the current state of the game, NOT STARTED, ONGOING, ENDED
    - Check the current player WHITE or BLACK
    - Check if The king in check
    - Take the move and apply it
    - Return error if the move is not valid in any previous step
 */
@Getter
public class Match {
    private final List<Move> movesHistory;
    Board board;
    MatchProgress matchProgress;

    public Match(BoardInitializer boardInitializer, List<Move> movesHistory) {
        State state = boardInitializer.initialState();
        this.movesHistory = movesHistory;
        this.board = state.board();
        this.matchProgress = state.matchProgress();
    }

    // TODO change all the Exception into a custom Exception
    // TODO check and check mate and all that nonsense -_-

    /**
     * TODO<br>
     * First need to check if the current player is in check
     *      <pre>
     *    if yes you check if the `move` will remove the player from begin checked
     *    if no you should check if the move will put him in check, then throw error
     *    or will put his opponent in check and then check if the check is a mate
     *      </pre>
     */
    public void apply(Move move) {
        Piece piece = move.piece();
        Pos from = move.from();
        Pos to = move.to();
        if (from == to) throw new IllegalMoveException("ARE YOU CRAZY!?");
        Piece inHand = board
                .getPiece(from)
                .orElseThrow(() -> new PieceNotFoundException("YOU WANNA MOVE AIR?"));
        if (piece != inHand) throw new PieceNotFoundException("STOP LYING");
        Optional<Piece> inTheWay = board.getPiece(to);
        if (move.takenPiece() != null && inTheWay.isEmpty()) {
            throw new PieceNotFoundException("YOU ARE TRYING TO TAKE THE WRONG PIECE YO PIECE OF ****");
        } else if (move.takenPiece() == null && inTheWay.isPresent()) {
            throw new IllegalMoveException("YOU ARE NOT WELCOMED!");
        }
        if (isInvalidPosition(from) || isInvalidPosition(to))
            throw new InvalidPositionException("GO LEARN CHESS PLEASE");
        if (!inHand.apply(move, this)) {
            throw new IllegalMoveException("THIS PIECE DOES NOT MOVE LIKE THAT!");
        }
        inTheWay.ifPresent(p -> {
            if (p.color() == inHand.color())
                throw new InvalidCaptureException("NO CANNIBALISM PLEASE");
        });
        // TODO calculate point if the piece took another piece
        board.putPiece(inHand, to);
        movesHistory.add(move);
        // TODO this should be changed to the new state
//        currentPlayer = currentPlayer == Color.BLACK ? Color.WHITE : Color.BLACK;
    }

    private boolean isInvalidPosition(Pos pos) {
        return pos.rank() < 0 || pos.rank() > 7 ||
                pos.file() < 0 || pos.file() > 7;
    }

}