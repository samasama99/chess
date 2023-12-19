package org.samasama.chess.service;

import org.samasama.chess.board.Match;
import org.samasama.chess.board.MatchFactory;
import org.samasama.chess.board.Mode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public record ChessServiceIMPL() implements ChessService {
    static Map<UUID, Match> matches = new HashMap<>();

    @Override
    public GameStateDTO createGame(Mode mode) {
        UUID uuid = UUID.randomUUID();
        Match match = MatchFactory.create(mode);
        matches.put(uuid, match);
        return new GameStateDTO(
                uuid,
                match.getBoard(),
                match.getState(),
                match.getNextMove(),
                match.getWinner()
        );
    }

    @Override
    public GameStateDTO move(MoveDTO moveDTO) throws Exception {
        UUID uuid = moveDTO.uuid();
        Match match = matches.get(uuid);
        match.move(moveDTO.from(), moveDTO.to());
        return new GameStateDTO(
                uuid,
                match.getBoard(),
                match.getState(),
                match.getNextMove(),
                match.getWinner()
        );
    }
}