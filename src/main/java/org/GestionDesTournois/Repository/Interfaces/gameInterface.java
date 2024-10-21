package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Game;

import java.util.List;
import java.util.Optional;

public interface gameInterface {
    boolean insertGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(int id);
    List<Game> getAllGames();
    Optional<Game> getGameById(int id);
}
