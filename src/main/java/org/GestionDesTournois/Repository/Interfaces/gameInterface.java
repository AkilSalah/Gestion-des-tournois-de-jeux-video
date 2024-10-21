package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Game;

import java.util.List;

public interface gameInterface {
    boolean insertGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(int id);
    List<Game> getAllGames();
    Game getGameById(int id);
}
