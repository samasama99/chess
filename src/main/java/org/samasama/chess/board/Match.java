package org.samasama.chess.board;

import lombok.Getter;
import org.graalvm.collections.Pair;
import org.samasama.chess.piece.Color;
import org.samasama.chess.piece.Move;
import org.samasama.chess.piece.Piece;
import org.samasama.chess.piece.Position;

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
    private final Board board;
    private final List<Move> movesHistory;
    int blackPoints = 0;
    int whitePoints = 0;
    private Winner winner;
    private Color nextMove;
    private State state;

    public Match(BoardInitializer boardInitializer, List<Move> movesHistory) {
        BoardInitializer.InitialState initialState = boardInitializer.initialState();
        this.movesHistory = movesHistory;
        board = initialState.board();
        winner = initialState.winner();
        nextMove = initialState.nextMove();
        state = initialState.state();
    }

    // TODO change all the Exception into a custom Exception
    // TODO check and check mate and all that nonsense -_-
    public void move(Move move) throws Exception {
        Piece piece = move.piece();
        Position from = move.from();
        Position to = move.to();
        if (from == to) throw new Exception("ARE YOU CRAZY!?");
        Piece inHand = board.getPiece(from).orElseThrow(() -> new Exception("YOU WANNA MOVE AIR?"));
        if (piece != inHand) throw new Exception("STOP LYING");
        Optional<Piece> inTheWay = board.getPiece(to);
        // TODO: check the from and to are valid board squares
        if (!inHand.validateMove(from, to, this)) {
            throw new Exception("THIS PIECE DOES NOT MOVE LIKE THAT!");
        }
        inTheWay.ifPresent(
                p -> {
                    if (p.color() == inHand.color())
                        throw new RuntimeException("NO CANNIBALISM PLEASE");
                }
        );
        // TODO calculate point if the piece took another piece
        int value = board.putPiece(inHand, to);
        switch (inHand.color()) {
            case BLACK -> blackPoints += value;
            case WHITE -> whitePoints += value;
        }
        movesHistory.add(move);
    }

}
