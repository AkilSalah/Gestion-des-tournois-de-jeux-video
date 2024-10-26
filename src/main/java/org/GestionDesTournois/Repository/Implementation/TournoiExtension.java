package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Repository.Interfaces.TournoiInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TournoiExtension implements TournoiInterface {

    private EntityManager em = JpaUtil.getInstance().getEntityManager();
    @Override
    public int calculerDureeEstimeeTournoi(int tournoiId){
        try {
           Tournoi tournoi = em.find(Tournoi.class,tournoiId);
           if (tournoi != null){
               int teamNb = tournoi.getTeams().size();
               int dureMoyenne = tournoi.getGame().getDureeMoyenneMatch();
               int tempsPause = tournoi.getTempsPause();
               int difficulte = tournoi.getGame().getDifficulte();
               int tempsCeremonies = tournoi.getTempsCeremonie();
               return (teamNb * dureMoyenne * difficulte)+(tempsPause)+(tempsCeremonies);
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean insertTournoi(Tournoi tournoi,List<Team> teams) {
        return false;
    }

    @Override
    public boolean updateTournoi(Tournoi tournoi) {
        return false;
    }

    @Override
    public boolean deleteTournoi(int id) {
        return false;
    }

    @Override
    public List<Tournoi> getAllTournois() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Tournoi> getTournoiById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteEquipe(int equipeId, int tournoiId) {
        return false;
    }
}
