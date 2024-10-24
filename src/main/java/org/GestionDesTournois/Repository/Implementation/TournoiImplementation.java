package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Repository.Interfaces.TournoiInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class TournoiImplementation implements TournoiInterface {

    private EntityManager getEntityManager(){
      return JpaUtil.getInstance().getEntityManager();
    }
    @Override
    public boolean insertTournoi(Tournoi tournoi) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(tournoi);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(em.isOpen() && em!=null){
                em.close();
            }
        }
    }
    @Override
    public boolean updateTournoi(Tournoi tournoi) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            Optional<Tournoi> tournoiOptional = getTournoiById(tournoi.getId());
            if (tournoiOptional.isPresent()) {
                transaction.begin();
                em.merge(tournoi);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em!=null && em.isOpen()) {
                em.close();
            }
        }
    }
    @Override
    public boolean deleteTournoi(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
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
            if (em!=null && em.isOpen()) {
                em.close();
            }        }
    }
    @Override
    public List<Tournoi> getAllTournois() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tournoi t", Tournoi.class).getResultList();
        } finally {
            em.close();
        }
    }
    @Override
    public Optional<Tournoi> getTournoiById(int id) {
        EntityManager em = getEntityManager();
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
