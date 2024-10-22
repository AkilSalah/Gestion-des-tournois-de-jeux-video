package org.GestionDesTournois.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le nom du jeu ne doit pas être vide")
    @Size(min = 4, max = 50, message = "Le nom du jeu doit comporter entre 2 et 50 caractères")
    private String nom;

    @Min(value = 1, message = "La difficulté doit être au minimum de 1")
    @Max(value = 3, message = "La difficulté doit être au maximum de 2")
    private int difficulte;

    @NotBlank(message = "La durée moyenne du match est obligatoire")
    @Min(value = 5, message = "La durée moyenne doit être d'au moins 5 minutes")
    private int dureeMoyenneMatch;

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
