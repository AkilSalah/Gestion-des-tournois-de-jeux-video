package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Player;

import java.util.List;
import java.util.Optional;

public interface playerInterface {
    boolean insertPlayer(Player player);
    boolean updatePlayer(Player player);
    boolean deletePlayer(int id);
    List<Player> getAllJoueurs();
    Optional<Player> getJoueurById(int id);
}
