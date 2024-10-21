package org.GestionDesTournois.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le nom de l'équipe ne peut pas être nul.")
    @Size(min = 1, max = 50, message = "Le nom de l'équipe doit contenir entre 1 et 50 caractères.")
    private String nom;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Player> players;

    @ManyToOne
    @JoinColumn(name = "tournoi_id")
    private Tournoi tournoi;

    @Min(value = 0, message = "Le classement doit être un nombre positif ou zéro.")
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
