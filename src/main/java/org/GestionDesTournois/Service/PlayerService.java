package org.GestionDesTournois.Service;

import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Repository.Implementation.PlayerImplementation;

import java.util.List;
import java.util.Optional;

public class PlayerService {
    private final PlayerImplementation playerRepository;

    public PlayerService(PlayerImplementation playerRepository){
        this.playerRepository = playerRepository;
    }
    public boolean addPlayer(Player player){
     return playerRepository.insertPlayer(player);
    }
    public boolean modifyPlayer(Player player){
        return playerRepository.updatePlayer(player);
    }
    public boolean removePlayer(int id){
        return playerRepository.deletePlayer(id);
    }
    public Optional<Player> getPlayerById(int id){
        return playerRepository.getJoueurById(id);
    }
    public List<Player> getAllPlayers(){
        return playerRepository.getAllJoueurs();
    }

}
