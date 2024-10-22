package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Tournoi;

import java.util.List;
import java.util.Optional;

public interface TournoiInterface {
    boolean insertTournoi(Tournoi tournoi);
    boolean updateTournoi(Tournoi tournoi);
    boolean deleteTournoi(int id);
    List<Tournoi> getAllTournois();
    Optional<Tournoi> getTournoiById(int id);
    double calculerDureeEstimeeTournoi(int tournoiId);
}
