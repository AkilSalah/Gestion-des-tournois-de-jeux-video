package org.GestionDesTournois.Service;

import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Repository.Implementation.TournoiExtension;
import org.GestionDesTournois.Repository.Implementation.TournoiImplementation;
import org.GestionDesTournois.Service.Interface.TournoiMetier;

import java.util.List;
import java.util.Optional;

public class TournoiMetierImpl implements TournoiMetier {
    private final TournoiImplementation tournoiRepository;
    private final TournoiExtension tournoiExtension;

    public TournoiMetierImpl(TournoiImplementation tournoiRepository,TournoiExtension tournoiExtension) {
        this.tournoiRepository = tournoiRepository;
        this.tournoiExtension = tournoiExtension;
    }
    @Override
    public double obtenirdureeEstimeeTournoi(int tournoiId) {
        return tournoiExtension.calculerDureeEstimeeTournoi(tournoiId);
    }
    public boolean addTournoi(Tournoi tournoi) {
        return tournoiRepository.insertTournoi(tournoi);
    }

    public boolean modifyTournoi(Tournoi tournoi) {
        Optional<Tournoi> existingTournoi = tournoiRepository.getTournoiById(tournoi.getId());
        if (existingTournoi.isPresent()) {
            return tournoiRepository.updateTournoi(tournoi);
        } else {
            return false;
        }
    }

    public boolean removeTournoi(int id) {
        return tournoiRepository.deleteTournoi(id);
    }

    public List<Tournoi> getAllTournois() {
        return tournoiRepository.getAllTournois();
    }

    public Optional<Tournoi> getTournoiById(int id) {
        return tournoiRepository.getTournoiById(id);
    }

}
