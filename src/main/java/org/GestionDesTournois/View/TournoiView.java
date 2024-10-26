package org.GestionDesTournois.View;

import org.GestionDesTournois.Models.*;
import org.GestionDesTournois.Service.GameService;
import org.GestionDesTournois.Service.TeamService;
import org.GestionDesTournois.Service.TournoiMetierImpl;
import org.GestionDesTournois.Utils.DateUtil;
import org.GestionDesTournois.Utils.LoggerUtil;
import org.GestionDesTournois.Utils.ValidationUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TournoiView {
    private TournoiMetierImpl tournoiService;
    private GameService gameService;
    private TeamService teamService;
    public TournoiView(TournoiMetierImpl tournoiService,GameService gameService,TeamService teamService) {
        this.tournoiService = tournoiService;
        this.gameService = gameService;
        this.teamService = teamService;
    }
    Scanner scanner = new Scanner(System.in);

    public void tournoiMenu() {
        int choice;
        do {
            LoggerUtil.logInfo("\n--- Menu Gestion des Tournoi ---");
            LoggerUtil.logInfo("1. Ajouter une tournoi");
            LoggerUtil.logInfo("2. Mettre à jour une tournoi");
            LoggerUtil.logInfo("3. Afficher tous les tournois");
            LoggerUtil.logInfo("4. Calcul de la durée totale estimée du tournoi");
            LoggerUtil.logInfo("5. Supprimer une équipe exist dans une tournoi");
            LoggerUtil.logInfo("6. Supprimer une tournoi");
            LoggerUtil.logInfo("7. Quitter");
            LoggerUtil.logInfo("Choisissez une option : ");

            choice = ValidationUtil.validationInt();

            switch (choice) {
                case 1:
                    addTournoi();
                    break;
                case 2:
                    updateTournoi();
                    break;
                case 3:
                    displayTournois();
                    break;
                case 4:
                    calculeDureeEstimee();
                    break;
                case 5:
                    removeTeamFromTournoi();
                    break;
                case 6:
                    deleteTournoi();
                    break;
                case 7:
                    LoggerUtil.logInfo("Quitter le menu des tournois.");
                    return;
                default:
                    LoggerUtil.logError("Option invalide, veuillez choisir entre 1 et 5.");
            }
        } while (choice != 7);
    }

    private void addTournoi() {
        LoggerUtil.logInfo("\n--- Ajouter Tournoi ---");

        try {
            Tournoi tournoi = new Tournoi();

            LoggerUtil.logInfo("Entrez le titre du tournoi : ");
            String titre = ValidationUtil.ValidationString();
            tournoi.setTitle(titre);

            Game game = null;
            while (game == null) {
                LoggerUtil.logInfo("Entrez le ID du jeu que vous voulez ajouter à ce tournoi : ");
                int gameId = ValidationUtil.validationInt();
                Optional<Game> optionalGame = gameService.getGameById(gameId);
                if (optionalGame.isPresent()) {
                    game = optionalGame.get();
                    tournoi.setGame(game);
                } else {
                    LoggerUtil.logError("Le jeu n'existe pas, veuillez réessayer");
                }
            }

            LocalDate dateDebut = null;
            while (dateDebut == null) {
                LoggerUtil.logInfo("Entrez la date de début (format : dd/MM/yyyy) : ");
                String dateStr = ValidationUtil.ValidationString();
                if (DateUtil.isValidDate(dateStr)) {
                    dateDebut = DateUtil.dateValidation(dateStr);
                    tournoi.setDateDebut(dateDebut);
                } else {
                    LoggerUtil.logError("Format de date invalide, veuillez réessayer");
                }
            }

            LocalDate dateFin = null;
            while (dateFin == null) {
                LoggerUtil.logInfo("Entrez la date de fin (format : dd/MM/yyyy) : ");
                String dateStr = ValidationUtil.ValidationString();
                if (DateUtil.isValidDate(dateStr)) {
                    dateFin = DateUtil.dateValidation(dateStr);
                    if (dateFin.isBefore(dateDebut)) {
                        LoggerUtil.logError("La date de fin doit être après la date de début");
                        continue;
                    }
                    tournoi.setDateFin(dateFin);
                } else {
                    LoggerUtil.logError("Format de date invalide, veuillez réessayer");
                }
            }

            LoggerUtil.logInfo("Entrez le nombre des spectateurs : ");
            int spectateurs = ValidationUtil.validationInt();
            tournoi.setNombreSpectateurs(spectateurs);

            LoggerUtil.logInfo("Entrez la durée estimée (en minutes) : ");
            int dureeEstimee = ValidationUtil.validationInt();
            tournoi.setDureeEstimee(dureeEstimee);

            LoggerUtil.logInfo("Entrez le temps de pause (en minutes) : ");
            int tempsPause = ValidationUtil.validationInt();
            tournoi.setTempsPause(tempsPause);

            LoggerUtil.logInfo("Entrez le temps de cérémonie (en minutes) : ");
            int tempsCeremonie = ValidationUtil.validationInt();
            tournoi.setTempsCeremonie(tempsCeremonie);

            LoggerUtil.logInfo("Choisissez le statut du tournoi :");
            LoggerUtil.logInfo("1. PLANIFIE");
            LoggerUtil.logInfo("2. EN_COURS");
            LoggerUtil.logInfo("3. TERMINE");
            LoggerUtil.logInfo("4. ANNULE");

            StatutTournoi statutTournoi = null;
            while (statutTournoi == null) {
                int choix = ValidationUtil.validationInt();
                switch (choix) {
                    case 1:
                        statutTournoi = StatutTournoi.PLANIFIE;
                        break;
                    case 2:
                        statutTournoi = StatutTournoi.EN_COURS;
                        break;
                    case 3:
                        statutTournoi = StatutTournoi.TERMINE;
                        break;
                    case 4:
                        statutTournoi = StatutTournoi.ANNULE;
                        break;
                    default:
                        LoggerUtil.logError("Option invalide, veuillez choisir entre 1 et 4");
                }
            }
            tournoi.setStatut(statutTournoi);

            List<Team> teams = new ArrayList<>();
            while (true) {
                LoggerUtil.logInfo("\nVoulez-vous ajouter une équipe au tournoi ? (O/N) : ");
                String reponse = ValidationUtil.ValidationString();
                if (!reponse.equalsIgnoreCase("O")) {
                    break;
                }

                LoggerUtil.logInfo("Entrez l'ID de l'équipe à ajouter : ");
                int teamId = ValidationUtil.validationInt();
                Optional<Team> optionalTeam = teamService.getTeamById(teamId);

                if (optionalTeam.isPresent()) {
                    Team team = optionalTeam.get();
                    teams.add(team);
                    LoggerUtil.logInfo("Équipe ajoutée avec succès");
                } else {
                    LoggerUtil.logError("Équipe introuvable");
                }
            }
            tournoi.setTeams(teams);

            boolean isAdded = tournoiService.addTournoi(tournoi,teams);
            if (isAdded) {
                LoggerUtil.logInfo("Le tournoi a été ajouté avec succès !");
            } else {
                LoggerUtil.logError("Erreur lors de l'ajout du tournoi");
            }

        } catch (Exception e) {
            LoggerUtil.logError("Une erreur est survenue : " + e.getMessage());
        }
    }

    public void updateTournoi() {
        LoggerUtil.logInfo("\n--- Mettre à jour un tournoi ---");
        LoggerUtil.logInfo("Entrez l'ID du tournoi à mettre à jour : ");
        int tournoiId = ValidationUtil.validationInt();
        Optional<Tournoi> optionalTournoi = tournoiService.getTournoiById(tournoiId);
        if (optionalTournoi.isEmpty()) {
            LoggerUtil.logError("Tournoi introuvable avec l'ID donné.");
            return;
        }

        Tournoi tournoi = optionalTournoi.get();

        LoggerUtil.logInfo("Entrez le nouveau titre du tournoi (actuel : " + tournoi.getTitle() + ") : ");
        String newTitle = ValidationUtil.ValidationString();
        if (!newTitle.isEmpty()) {
            tournoi.setTitle(newTitle);
        }

        LoggerUtil.logInfo("Voulez-vous modifier le jeu associé ? (actuel : " + tournoi.getGame().getNom() + ") (y/n) : ");
        String modifyGame = ValidationUtil.ValidationString();
        if (modifyGame.equalsIgnoreCase("y")) {
            Game game = null;
            while (game == null) {
                LoggerUtil.logInfo("Entrez l'ID du nouveau jeu : ");
                int gameId = ValidationUtil.validationInt();
                Optional<Game> optionalGame = gameService.getGameById(gameId);
                if (optionalGame.isPresent()) {
                    game = optionalGame.get();
                    tournoi.setGame(game);
                } else {
                    LoggerUtil.logError("Le jeu n'existe pas.");
                }
            }
        }

        LocalDate dateDebut = null;
        do {
            LoggerUtil.logInfo("Entrez la nouvelle date de début (actuelle : " + tournoi.getDateDebut() + ", format : dd/MM/yyyy) : ");
            String newDateDebut = ValidationUtil.ValidationString();
            if (newDateDebut.isEmpty() || DateUtil.isValidDate(newDateDebut)) {
                if (!newDateDebut.isEmpty()) {
                    dateDebut = DateUtil.dateValidation(newDateDebut);
                    tournoi.setDateDebut(dateDebut);
                }
                break;
            } else {
                LoggerUtil.logInfo("La date est invalide. Veuillez réessayer.");
            }
        } while (true);

        LocalDate dateFin = null;
        do {
            LoggerUtil.logInfo("Entrez la nouvelle date de fin (actuelle : " + tournoi.getDateFin() + ", format : dd/MM/yyyy) : ");
            String newDateFin = ValidationUtil.ValidationString();
            if (newDateFin.isEmpty() || DateUtil.isValidDate(newDateFin)) {
                if (!newDateFin.isEmpty()) {
                    dateFin = DateUtil.dateValidation(newDateFin);
                    if (dateFin != null && (dateDebut == null || !dateFin.isBefore(tournoi.getDateDebut()))) {
                        tournoi.setDateFin(dateFin);
                    } else {
                        LoggerUtil.logWarn("La date de fin ne peut pas être antérieure à la date de début.");
                        continue;
                    }
                }
                break;
            } else {
                LoggerUtil.logInfo("La date est invalide. Veuillez réessayer.");
            }
        } while (true);

        // Update Number of Spectators
        LoggerUtil.logInfo("Entrez le nouveau nombre de spectateurs (actuel : " + tournoi.getNombreSpectateurs() + ") : ");
        String newSpectateurs = ValidationUtil.ValidationString();
        if (!newSpectateurs.isEmpty()) {
            int spectateurs = Integer.parseInt(newSpectateurs);
            tournoi.setNombreSpectateurs(spectateurs);
        }

        LoggerUtil.logInfo("Entrez la nouvelle durée estimée (actuelle : " + tournoi.getDureeEstimee() + ") : ");
        String newDureeEstimee = ValidationUtil.ValidationString();
        if (!newDureeEstimee.isEmpty()) {
            int dureeEstimee = Integer.parseInt(newDureeEstimee);
            tournoi.setDureeEstimee(dureeEstimee);
        }

        LoggerUtil.logInfo("Entrez le nouveau temps de pause (actuel : " + tournoi.getTempsPause() + ") : ");
        String newTempsPause = ValidationUtil.ValidationString();
        if (!newTempsPause.isEmpty()) {
            int tempsPause = Integer.parseInt(newTempsPause);
            tournoi.setTempsPause(tempsPause);
        }

        LoggerUtil.logInfo("Entrez le nouveau temps de cérémonie (actuel : " + tournoi.getTempsCeremonie() + ") : ");
        String newTempsCeremonie = ValidationUtil.ValidationString();
        if (!newTempsCeremonie.isEmpty()) {
            int tempsCeremonie = Integer.parseInt(newTempsCeremonie);
            tournoi.setTempsCeremonie(tempsCeremonie);
        }

        StatutTournoi statutTournoi = null;
        do {
            LoggerUtil.logInfo("Entrez le nouveau statut du tournoi (actuel : " + tournoi.getStatut() + ")");
            LoggerUtil.logInfo("1.PLANIFIE");
            LoggerUtil.logInfo("2.EN_COURS");
            LoggerUtil.logInfo("3.TERMINE");
            LoggerUtil.logInfo("4.ANNULE");
            String newStatut = ValidationUtil.ValidationString();
            if (!newStatut.isEmpty()) {
                int choice = Integer.parseInt(newStatut);
                switch (choice) {
                    case 1 -> statutTournoi = StatutTournoi.PLANIFIE;
                    case 2 -> statutTournoi = StatutTournoi.EN_COURS;
                    case 3 -> statutTournoi = StatutTournoi.TERMINE;
                    case 4 -> statutTournoi = StatutTournoi.ANNULE;
                    default -> LoggerUtil.logWarn("Option invalide, veuillez choisir une option valide.");
                }
            }
            if (statutTournoi != null) {
                tournoi.setStatut(statutTournoi);
            }
        } while (statutTournoi == null);

        boolean isUpdated = tournoiService.modifyTournoi(tournoi);
        if (isUpdated) {
            LoggerUtil.logInfo("Le tournoi a été mis à jour avec succès.");
        } else {
            LoggerUtil.logError("Erreur lors de la mise à jour du tournoi.");
        }
    }


    public void deleteTournoi(){
        LoggerUtil.logInfo("\n--- Supprimer une tournoi ---");
        LoggerUtil.logInfo("Entrez l'ID du tournoi à supprimer : ");
        int id = ValidationUtil.validationInt();
        boolean isRemoved = tournoiService.removeTournoi(id);
        if (isRemoved) {
            LoggerUtil.logInfo("La tournoi a été supprimé avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de la suppression du tournoi.");
        }
    }

    public void displayTournois() {
        LoggerUtil.logInfo("\n--- Liste de tous les tournois ---");
        List<Tournoi> tournois = tournoiService.getAllTournois();

        if (tournois.isEmpty()) {
            LoggerUtil.logInfo("Aucun tournoi trouvé.");
        } else {
            tournois.forEach(tournoi -> {
                LoggerUtil.logInfo(tournoi.toString());

                if (tournoi.getTeams() != null && !tournoi.getTeams().isEmpty()) {
                    LoggerUtil.logInfo("Nombre d'équipes : " + tournoi.getTeams().size());
                    tournoi.getTeams().forEach(team -> LoggerUtil.logInfo(team.toString()));
                } else {
                    LoggerUtil.logInfo("Aucune équipe associée.");
                }
            });
        }
    }
    public void calculeDureeEstimee() {
        LoggerUtil.logInfo("\n--- Calculate duree estimee ---");
        LoggerUtil.logInfo("Entrez l'ID du tournoi :  ");
        int tournoiId = ValidationUtil.validationInt();
        Optional<Tournoi> tournoiOptional = tournoiService.getTournoiById(tournoiId);
        if (tournoiOptional.isPresent()) {
            Tournoi tournoi = tournoiOptional.get();
          double calcul =  tournoiService.obtenirdureeEstimeeTournoi(tournoiId);
          LoggerUtil.logInfo("La duree estimee du tournoi avec id " +tournoi.getId()+ " : " + calcul);
        }else{
            LoggerUtil.logInfo("La tournoi n'existe pas ! ");
        }
    }

    private void removeTeamFromTournoi() {
        LoggerUtil.logInfo("\n--- Supprimer une équipe d'un tournoi ---");

        LoggerUtil.logInfo("Entrez l'ID du tournoi : ");
        int tournoiId = ValidationUtil.validationInt();

        Optional<Tournoi> tournoiOptional = tournoiService.getTournoiById(tournoiId);
        if (tournoiOptional.isEmpty()) {
            LoggerUtil.logError("Tournoi non trouvé avec l'ID fourni.");
            return;
        }

        Tournoi tournoi = tournoiOptional.get();
        List<Team> teams = tournoi.getTeams();
        if (teams.isEmpty()) {
            LoggerUtil.logInfo("Aucune équipe trouvée dans ce tournoi.");
            return;
        }

        LoggerUtil.logInfo("Liste des équipes dans le tournoi '" + tournoi.getTitle() + "':");
        for (Team team : teams) {
            LoggerUtil.logInfo(team.toString());
        }

        LoggerUtil.logInfo("Entrez l'ID de l'équipe à supprimer : ");
        int equipeId = ValidationUtil.validationInt();

        boolean isDeleted = tournoiService.removeTeamFromTournoi(equipeId, tournoiId); // Assuming the method is renamed to deleteEquipe
        if (isDeleted) {
            LoggerUtil.logInfo("L'équipe a été supprimée avec succès du tournoi.");
        } else {
            LoggerUtil.logError("Échec de la suppression de l'équipe.");
        }
    }




}
