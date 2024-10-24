package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Repository.Interfaces.PlayerInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class PlayerImplementation implements PlayerInterface {

    EntityManager getEntityManager() {
        return JpaUtil.getInstance().getEntityManager();
    }
    @Override
    public boolean insertPlayer(Player player) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(player);
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean updatePlayer(Player player) {
        EntityManager em = getEntityManager();
        try {
            Optional<Player> playerOptional = getJoueurById(player.getId());
            if (playerOptional.isPresent()){
                em.getTransaction().begin();
                em.merge(player);
                em.getTransaction().commit();
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean deletePlayer(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Player player = em.find(Player.class,id);
            if (player != null){
                em.remove(player);
                em.getTransaction().commit();
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Player> getAllJoueurs() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Player p",Player.class).getResultList();
        }finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Optional<Player> getJoueurById(int id) {
        EntityManager em = getEntityManager();
        try {
            Player player = em.find(Player.class,id);
            return Optional.ofNullable(player);
        }finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
