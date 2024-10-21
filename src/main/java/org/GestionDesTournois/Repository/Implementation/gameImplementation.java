package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.GestionDesTournois.Models.Game;
import org.GestionDesTournois.Repository.Interfaces.gameInterface;
import org.GestionDesTournois.Utils.JpaUtil;
import java.util.List;
import java.util.Optional;

public class gameImplementation implements gameInterface {

    EntityManager em = JpaUtil.getInstance().getEntityManager();

    @Override
    public boolean insertGame(Game game) {
        try {
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }    }

    @Override
    public boolean updateGame(Game game) {
        try {
            em.getTransaction().begin();
            em.merge(game);
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
    public boolean deleteGame(int id) {
        try {
            em.getTransaction().begin();
            Game game = em.find(Game.class, id);
            if (game != null) {
                em.remove(game);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }    }

    @Override
    public List<Game> getAllGames() {
        try {
            TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g", Game.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


    @Override
    public Optional<Game> getGameById(int id) {
        try {
            Game game = em.find(Game.class, id);
            return Optional.ofNullable(game);
        } finally {
            em.close();
        }
    }
}
