package org.GestionDesTournois.Repository.Interfaces;

import org.GestionDesTournois.Models.Tournoi;

import java.util.List;

public interface TournoiInterface {
    boolean insertTournoi(Tournoi tournoi);
    boolean updateTournoi(Tournoi tournoi);
    boolean deleteTournoi(int id);
    List<Tournoi> getAllTournois();
    Tournoi getTournoiById(int id);
    int calculerDureeEstimeeTournoi(int tournoiId);
}
