package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Repository.Interfaces.TournoiInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class TournoiImplementation implements TournoiInterface {

    private EntityManager em = JpaUtil.getInstance().getEntityManager();
    @Override
    public boolean insertTournoi(Tournoi tournoi) {
        try {
            em.getTransaction().begin();
            em.persist(tournoi);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public boolean updateTournoi(Tournoi tournoi) {
        try {
            Optional<Tournoi> tournoiOptional = getTournoiById(tournoi.getId());
            if (tournoiOptional.isPresent()) {
                em.getTransaction().begin();
                em.merge(tournoi);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public boolean deleteTournoi(int id) {
        try {
            em.getTransaction().begin();
            Tournoi tournoi = em.find(Tournoi.class, id);
            if (tournoi != null) {
                em.remove(tournoi);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public List<Tournoi> getAllTournois() {
        try {
            return em.createQuery("SELECT t FROM Tournoi t", Tournoi.class).getResultList();
        } finally {
            em.close();
        }
    }
    @Override
    public Optional<Tournoi> getTournoiById(int id) {
        try {
            Tournoi tournoi = em.find(Tournoi.class, id);
            return Optional.ofNullable(tournoi);
        } finally {
            em.close();
        }
    }

    @Override
    public int calculerDureeEstimeeTournoi(int tournoiId) {
        Optional<Tournoi> tournoi = getTournoiById(tournoiId);
        if (tournoi.isPresent()){
            Tournoi tournoi1 = tournoi.get();
            int teamNb = tournoi1.getTeams().size();
            int dureMoyenne = tournoi1.getDureeEstimee();
            int tempsPause = tournoi1.getTempsPause();
            return (teamNb * dureMoyenne)+tempsPause;
        }
        return 0;
    }
}
