package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Team;

import java.util.List;
import java.util.Optional;

public interface TeamInterface {
    boolean insertTeam(Team team);
    boolean updateTeam(Team team);
    boolean deleteTeam(int id);
    List<Team> getAllTeams();
    Optional<Team> getTeamById(int id);
}
