package org.samasama.chess.service;

import org.junit.jupiter.api.Test;
import org.samasama.chess.match.Mode;

class ChessServiceIMPLTest {

    ChessService chessService = new ChessServiceIMPL();

    @Test
    void createGame() {
        ChessService.GameStateDTO gameStateDTO = chessService.createGame(Mode.NORMAL);
        System.out.println(gameStateDTO.uuid());
        System.out.println(gameStateDTO.matchProgress());
        System.out.println(gameStateDTO.board());
    }

    @Test
    void move() {
    }
}