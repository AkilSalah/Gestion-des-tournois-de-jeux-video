package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Repository.Interfaces.TeamInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class TeamImplementation implements TeamInterface {
    private EntityManager getEntityManager(){
        return JpaUtil.getInstance().getEntityManager();
    }
    @Override
    public boolean insertTeam(Team team) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(team);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    @Override
    public boolean updateTeam(Team team) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            Optional<Team> teamOptional = getTeamById(team.getId());
            if (teamOptional.isPresent()) {
                transaction.begin();
                em.merge(team);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    @Override
    public boolean deleteTeam(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Team team = em.find(Team.class, id);
            if (team != null) {
                em.remove(team);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    @Override
    public List<Team> getAllTeams() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Team t LEFT JOIN FETCH t.players", Team.class).getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Optional<Team> getTeamById(int id) {
        EntityManager em = getEntityManager();
        try {
            Team team = em.find(Team.class, id);
            return Optional.ofNullable(team);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
