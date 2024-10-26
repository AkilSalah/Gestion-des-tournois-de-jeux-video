package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Models.Tournoi;

import java.util.List;
import java.util.Optional;

public interface TournoiInterface {
    boolean insertTournoi(Tournoi tournoi,List<Team> teams);
    boolean updateTournoi(Tournoi tournoi);
    boolean deleteTournoi(int id);
    List<Tournoi> getAllTournois();
    boolean deleteEquipe(int equipeId,int tournoiId);
    Optional<Tournoi> getTournoiById(int id);
    int calculerDureeEstimeeTournoi(int tournoiId);
}
