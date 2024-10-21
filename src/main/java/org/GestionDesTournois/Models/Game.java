package org.GestionDesTournois.Models;

public class Game {
    private int id;
    private String nom ;
    private int difficulte;
    private  int dureeMoyenneMatch;

    public Game(int id, String nom, int difficulte, int dureeMoyenneMatch) {
        this.id = id;
        this.nom = nom;
        this.difficulte = difficulte;
        this.dureeMoyenneMatch = dureeMoyenneMatch;
    }

    public Game() {
    }

    public Game(String nom, int difficulte, int dureeMoyenneMatch) {
        this.nom = nom;
        this.difficulte = difficulte;
        this.dureeMoyenneMatch = dureeMoyenneMatch;
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

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public int getDureeMoyenneMatch() {
        return dureeMoyenneMatch;
    }

    public void setDureeMoyenneMatch(int dureeMoyenneMatch) {
        this.dureeMoyenneMatch = dureeMoyenneMatch;
    }

    @Override
    public String toString() {
        return "game{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", difficulte=" + difficulte +
                ", dureeMoyenneMatch=" + dureeMoyenneMatch +
                '}';
    }
}
