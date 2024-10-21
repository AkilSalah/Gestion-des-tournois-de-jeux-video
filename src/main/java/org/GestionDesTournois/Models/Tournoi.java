package org.GestionDesTournois.Models;

import java.time.LocalDate;
import java.util.List;

public class Tournoi {
    private int id;
    private String title;
    private Game game;
    private LocalDate dateDebut ;
    private LocalDate dateFin;
    private int nombreSpectateurs;
    private List<Team> teams;
    private int dureeEstimee;
    private int tempsPause;
    private int tempsCeremonie;
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
