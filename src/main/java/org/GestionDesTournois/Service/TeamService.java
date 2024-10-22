package org.GestionDesTournois.Service;

import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Repository.Implementation.TeamImplementation;

import java.util.List;
import java.util.Optional;

public class TeamService {
    private final TeamImplementation teamRepository;

    public TeamService(TeamImplementation teamRepository) {
        this.teamRepository = teamRepository;
    }

    public boolean addTeam(Team team) {
        return teamRepository.insertTeam(team);
    }

    public boolean modifyTeam(Team team) {
        Optional<Team> existingTeam = teamRepository.getTeamById(team.getId());
        if (existingTeam.isPresent()) {
            return teamRepository.updateTeam(team);
        } else {
            return false;
        }
    }

    public boolean removeTeam(int id) {
        return teamRepository.deleteTeam(id);
    }

    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    public Optional<Team> getTeamById(int id) {
        return teamRepository.getTeamById(id);
    }
}
