package org.samasama.chess.board;

import org.samasama.chess.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.samasama.chess.piece.Piece.BLACK_PAWN;
import static org.samasama.chess.piece.Piece.WHITE_PAWN;

/**
 * P: White pawn<br>
 * N: White knight<br>
 * B: White bishop<br>
 * R: White rook<br>
 * Q: White queen<br>
 * K: White king<br>
 * p: Black pawn<br>
 * n: Black knight<br>
 * b: Black bishop<br>
 * r: Black rook<br>
 * q: Black queen<br>
 * k: Black king<br>
 */
public class BoardAdapter {

    private static Optional<Piece> adapt(char c) {
        return switch (c) {
            case 'p':
                yield Optional.of(BLACK_PAWN);
            case 'P':
                yield Optional.of(WHITE_PAWN);
            case '.':
                yield Optional.empty();
            default:
                throw new RuntimeException("THERE NO PIECE WITH THIS SYMBOL");
        };
    }

    public static Board from(String map) {
        char[][] arr = Arrays
                .stream(map.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return from(arr);
    }

    public static Board from(char[][] map) {
        List<List<Optional<Piece>>> board = new ArrayList<>(8);
        IntStream.range(0, 8).forEach(i -> board.add(new ArrayList<>(8)));
        int index = 0;
        for (List<Optional<Piece>> row : board) {
            int _row = index;
            IntStream.range(0, 8).forEach(_col -> row.add(adapt(map[_row][_col])));
            index++;
        }
        return new Board(board);
    }

    // MAIN FOR TESTING
    public static void main(String[] args) {
        System.out.println(from(
                """
                        ........
                        PPPPPPPP
                        ........
                        ........
                        ........
                        ........
                        ........
                        pppppppp
                        ........
                        """
        ));
    }
}
