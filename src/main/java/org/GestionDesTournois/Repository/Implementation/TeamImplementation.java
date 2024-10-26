package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Repository.Interfaces.TeamInterface;
import org.GestionDesTournois.Utils.JpaUtil;
import org.GestionDesTournois.Utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamImplementation implements TeamInterface {
    private EntityManager getEntityManager(){
        return JpaUtil.getInstance().getEntityManager();
    }
    @Override
    public boolean insertTeam(Team team, List<Player> players) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            for (Player player : players) {
                player.setEquipe(team);
            }

            team.getPlayers().addAll(players);

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
    public Optional<Team> getTeamById(int teamId) {
        EntityManager em = getEntityManager();
        Team team = em.find(Team.class, teamId);
        if (team != null) {
            team.getPlayers().size();
        }
        return Optional.ofNullable(team);
    }

    @Override
    public boolean deleteJoueur(int joueurId, int teamId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Optional<Team> teamOptional = getTeamById(teamId);
            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                Player playerToRemove = null;

                for (Player player : new ArrayList<>(team.getPlayers())) {
                    if (player.getId() == joueurId) {
                        playerToRemove = player;
                        break;
                    }
                }

                if (playerToRemove != null) {
                    team.getPlayers().remove(playerToRemove);

                    Player managedPlayer = em.find(Player.class, joueurId);
                    if (managedPlayer != null) {
                        managedPlayer.setEquipe(null);
                    }

                    em.merge(team);
                    em.getTransaction().commit();
                    return true;
                } else {
                    LoggerUtil.logInfo("Joueur non trouvé dans l'équipe.");
                }
            } else {
                LoggerUtil.logInfo("Équipe non trouvée.");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
