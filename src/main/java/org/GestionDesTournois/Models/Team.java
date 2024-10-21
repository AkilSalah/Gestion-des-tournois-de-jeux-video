package org.GestionDesTournois.Models;

import java.util.List;

public class Team {
    private int id;
    private String nom;
    private List<Player> players;
    private Tournoi tournoi;
    private int classement;

    public Team(int id, String nom, List<Player> players, Tournoi tournoi, int classement) {
        this.id = id;
        this.nom = nom;
        this.players = players;
        this.tournoi = tournoi;
        this.classement = classement;
    }

    public Team() {
    }

    public Team(String nom, List<Player> players, Tournoi tournoi, int classement) {
        this.nom = nom;
        this.players = players;
        this.tournoi = tournoi;
        this.classement = classement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    @Override
    public String toString() {
        return "team{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", players=" + players +
                ", tournoi=" + tournoi +
                ", classement=" + classement +
                '}';
    }
}
