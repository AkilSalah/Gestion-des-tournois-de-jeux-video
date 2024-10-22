package org.GestionDesTournois.Repository.Implementation;

import jakarta.persistence.EntityManager;
import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Repository.Interfaces.PlayerInterface;
import org.GestionDesTournois.Utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class PlayerImplementation implements PlayerInterface {

    EntityManager em = JpaUtil.getInstance().getEntityManager();

    @Override
    public boolean insertPlayer(Player player) {
        try {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            em.close();
        }
    }

    @Override
    public boolean updatePlayer(Player player) {
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
            em.close();
        }
    }

    @Override
    public boolean deletePlayer(int id) {
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
            em.close();
        }
    }

    @Override
    public List<Player> getAllJoueurs() {
        try {
            return em.createQuery("SELECT p FROM Player p",Player.class).getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public Optional<Player> getJoueurById(int id) {
        try {
            Player player = em.find(Player.class,id);
            return Optional.ofNullable(player);
        }finally {
            em.close();
        }
    }
}
