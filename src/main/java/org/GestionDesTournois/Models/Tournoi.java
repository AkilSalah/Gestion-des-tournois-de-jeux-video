package org.GestionDesTournois.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tournois")
public class Tournoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Le titre du tournoi ne peut pas être nul.")
    @Size(min = 5, max = 100, message = "Le titre du tournoi doit contenir entre 1 et 100 caractères.")
    private String title;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @NotNull(message = "Le jeu associé ne peut pas être nul.")
    private Game game;

    @NotNull(message = "La date de début ne peut pas être nulle.")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin ne peut pas être nulle.")
    private LocalDate dateFin;

    @Min(value = 0, message = "Le nombre de spectateurs doit être positif ou zéro.")
    private int nombreSpectateurs;

    @OneToMany(mappedBy = "tournoi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    @Min(value = 0, message = "La durée estimée doit être positive ou zéro.")
    private int dureeEstimee;

    @Min(value = 0, message = "Le temps de pause doit être positif ou zéro.")
    private int tempsPause;

    @Min(value = 0, message = "Le temps de cérémonie doit être positif ou zéro.")
    private int tempsCeremonie;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut du tournoi ne peut pas être nul.")
    private StatutTournoi statut;
    public Tournoi(int id, String title, Game game, LocalDate dateDebut, LocalDate dateFin, int nombreSpectateurs, List<Team> teams, int dureeEstimee, int tempsPause, int tempsCeremonie, StatutTournoi statut) {
        this.id = id;
        this.title = title;
        this.game = game;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreSpectateurs = nombreSpectateurs;
        this.teams = teams;
        this.dureeEstimee = dureeEstimee;
        this.tempsPause = tempsPause;
        this.tempsCeremonie = tempsCeremonie;
        this.statut = statut;
    }

    public Tournoi() {
    }

    public Tournoi(String title, Game game, LocalDate dateDebut, LocalDate dateFin, int nombreSpectateurs, List<Team> teams, int dureeEstimee, int tempsPause, int tempsCeremonie, StatutTournoi statut) {
        this.title = title;
        this.game = game;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreSpectateurs = nombreSpectateurs;
        this.teams = teams;
        this.dureeEstimee = dureeEstimee;
        this.tempsPause = tempsPause;
        this.tempsCeremonie = tempsCeremonie;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getNombreSpectateurs() {
        return nombreSpectateurs;
    }

    public void setNombreSpectateurs(int nombreSpectateurs) {
        this.nombreSpectateurs = nombreSpectateurs;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(int dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public int getTempsPause() {
        return tempsPause;
    }

    public void setTempsPause(int tempsPause) {
        this.tempsPause = tempsPause;
    }

    public int getTempsCeremonie() {
        return tempsCeremonie;
    }

    public void setTempsCeremonie(int tempsCeremonie) {
        this.tempsCeremonie = tempsCeremonie;
    }

    public StatutTournoi getStatut() {
        return statut;
    }

    public void setStatut(StatutTournoi statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "tournoi{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", game=" + game +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nombreSpectateurs=" + nombreSpectateurs +
                ", teams=" + teams +
                ", dureeEstimee=" + dureeEstimee +
                ", tempsPause=" + tempsPause +
                ", tempsCeremonie=" + tempsCeremonie +
                ", statut=" + statut +
                '}';
    }
}
