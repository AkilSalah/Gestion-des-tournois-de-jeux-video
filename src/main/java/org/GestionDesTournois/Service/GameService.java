package org.GestionDesTournois.Service;

import org.GestionDesTournois.Models.Game;
import org.GestionDesTournois.Repository.Implementation.GameImplementation;

import java.util.List;
import java.util.Optional;

public class GameService {
    private final GameImplementation gameRepository;

    public GameService(GameImplementation gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean addGame(Game game) {
        return gameRepository.insertGame(game);
    }

    public boolean modifyGame(Game game) {
        Optional<Game> existingGame = gameRepository.getGameById(game.getId());
        if (existingGame.isPresent()) {
            return gameRepository.updateGame(game);
        } else {
            return false;
        }
    }
    public boolean removeGame(int id) {
        return gameRepository.deleteGame(id);
    }

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public Optional<Game> getGameById(int id) {
        return gameRepository.getGameById(id);
    }
}

