package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Player;

import java.util.List;

public interface playerInterface {
    boolean insertPlayer(Player player);
    boolean updatePlayer(Player player);
    boolean deletePlayer(int id);
    List<Player> getAllJoueurs();
    Player getJoueurById(int id);
}
