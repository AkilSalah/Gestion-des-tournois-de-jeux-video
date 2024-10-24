package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.GestionDesTournois.Models.Game;
import org.GestionDesTournois.Repository.Interfaces.GameInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class GameImplementation implements GameInterface {

    private EntityManager getEntityManager() {
        return JpaUtil.getInstance().getEntityManager();
    }

    @Override
    public boolean insertGame(Game game) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(game);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean updateGame(Game game) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            Optional<Game> gameOptional = getGameById(game.getId());
            if (gameOptional.isPresent()) {
                transaction.begin();
                em.merge(game);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean deleteGame(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Game game = em.find(Game.class, id);
            if (game != null) {
                em.remove(game);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Game> getAllGames() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT g FROM Game g", Game.class).getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Optional<Game> getGameById(int id) {
        EntityManager em = getEntityManager();
        try {
            Game game = em.find(Game.class, id);
            return Optional.ofNullable(game);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
