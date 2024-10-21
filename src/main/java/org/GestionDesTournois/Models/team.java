package org.GestionDesTournois.Models;

import java.util.List;

public class team {
    private int id;
    private String nom;
    private List<player> players;
    private tournoi tournoi;
    private int classement;

    public team(int id, String nom, List<player> players, org.GestionDesTournois.Models.tournoi tournoi, int classement) {
        this.id = id;
        this.nom = nom;
        this.players = players;
        this.tournoi = tournoi;
        this.classement = classement;
    }

    public team() {
    }

    public team(String nom, List<player> players, org.GestionDesTournois.Models.tournoi tournoi, int classement) {
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

    public List<player> getPlayers() {
        return players;
    }

    public void setPlayers(List<player> players) {
        this.players = players;
    }

    public org.GestionDesTournois.Models.tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(org.GestionDesTournois.Models.tournoi tournoi) {
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
