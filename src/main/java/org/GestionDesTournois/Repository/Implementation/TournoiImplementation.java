package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Repository.Interfaces.TournoiInterface;
import org.GestionDesTournois.Utils.JpaUtil;
import org.GestionDesTournois.Utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TournoiImplementation implements TournoiInterface {

    private EntityManager getEntityManager(){
      return JpaUtil.getInstance().getEntityManager();
    }
    @Override
    public boolean insertTournoi(Tournoi tournoi, List<Team> teams) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            tournoi.setTeams(new ArrayList<>());
            em.persist(tournoi);

            for (Team team : teams) {
                Team managedTeam = em.find(Team.class, team.getId());
                if (managedTeam != null) {
                    managedTeam.setTournoi(tournoi);
                    tournoi.getTeams().add(managedTeam);
                    em.merge(managedTeam);
                }
            }

            em.merge(tournoi);

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em.isOpen()) {
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
            if (em.isOpen()) {
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
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    @Override
    public List<Tournoi> getAllTournois() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tournoi t LEFT JOIN FETCH t.teams", Tournoi.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    @Override
    public Optional<Tournoi> getTournoiById(int id) {
        EntityManager em = getEntityManager();
        try {
            Tournoi tournoi = em.find(Tournoi.class, id);
            if (tournoi != null) {
                tournoi.getTeams().size();
            }
            return Optional.ofNullable(tournoi);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
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

    @Override
    public boolean deleteEquipe(int equipeId, int tournoiId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Optional<Tournoi> tournoiOptional = getTournoiById(tournoiId);
            if (tournoiOptional.isPresent()) {
                Tournoi tournoi = tournoiOptional.get();
                Team teamToRemove = null;

                for (Team team : new ArrayList<>(tournoi.getTeams())) {
                    if (team.getId() == equipeId) {
                        teamToRemove = team;
                        break;
                    }
                }

                if (teamToRemove != null) {
                    tournoi.getTeams().remove(teamToRemove);
                    Team managedTeam = em.find(Team.class, equipeId);
                    if (managedTeam != null) {
                        managedTeam.setTournoi(null);
                    }
                    em.merge(tournoi);
                    em.getTransaction().commit();
                    return true;
                } else {
                    LoggerUtil.logInfo("Équipe non trouvée dans le tournoi.");
                }
            } else {
                LoggerUtil.logInfo("Tournoi non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
        return false;
    }


}
