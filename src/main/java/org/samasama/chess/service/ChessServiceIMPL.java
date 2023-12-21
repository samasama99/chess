package org.samasama.chess.service;

import org.samasama.chess.match.Match;
import org.samasama.chess.match.MatchFactory;
import org.samasama.chess.match.Mode;
import org.samasama.chess.piece.Move;
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
                match.getMatchProgress()
        );
    }

    @Override
    public GameStateDTO move(MoveDTO moveDTO) {
        UUID uuid = moveDTO.uuid();
        Match match = matches.get(uuid);
        match.apply(Move.from(moveDTO));
        return new GameStateDTO(
                uuid,
                match.getBoard(),
                match.getMatchProgress()
        );
    }
}
