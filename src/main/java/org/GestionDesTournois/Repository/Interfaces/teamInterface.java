package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Team;

import java.util.List;
import java.util.Optional;

public interface teamInterface {
    boolean insertEquipe(Team team);
    boolean updateEquipe(Team team);
    boolean deleteEquipe(int id);
    List<Team> getAllEquipes();
    Optional<Team> getEquipeById(int id);
}
