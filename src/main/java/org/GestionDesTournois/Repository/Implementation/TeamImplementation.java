package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Repository.Interfaces.TeamInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class TeamImplementation implements TeamInterface {
    private EntityManager em = JpaUtil.getInstance().getEntityManager();

    @Override
    public boolean insertTeam(Team team) {
        try {
            em.getTransaction().begin();
            em.persist(team);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }
    @Override
    public boolean updateTeam(Team team) {
        try {
            Optional<Team> teamOptional = getTeamById(team.getId());
            if (teamOptional.isPresent()) {
                em.getTransaction().begin();
                em.merge(team);
                em.getTransaction().commit();
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
            em.close();
        }
    }
    @Override
    public boolean deleteTeam(int id) {
        try {
            em.getTransaction().begin();
            Team team = em.find(Team.class, id);
            if (team != null) {
                em.remove(team);
                em.getTransaction().commit();
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
            em.close();
        }
    }
    @Override
    public List<Team> getAllTeams() {
        try {
            return em.createQuery("SELECT t FROM Team t", Team.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Team> getTeamById(int id) {
        try {
            Team team = em.find(Team.class, id);
            return Optional.ofNullable(team);
        } finally {
            em.close();
        }
    }
}
