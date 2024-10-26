package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Models.Team;

import java.util.List;
import java.util.Optional;

public interface TeamInterface {
    boolean insertTeam(Team team,List<Player> players);
    boolean updateTeam(Team team);
    boolean deleteTeam(int id);
    boolean deleteJoueur(int joueurId,int teamId);
    List<Team> getAllTeams();
    Optional<Team> getTeamById(int id);
}
