package org.GestionDesTournois.View;

import org.GestionDesTournois.Models.Game;
import org.GestionDesTournois.Models.StatutTournoi;
import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Service.GameService;
import org.GestionDesTournois.Service.TournoiMetierImpl;
import org.GestionDesTournois.Utils.DateUtil;
import org.GestionDesTournois.Utils.LoggerUtil;
import org.GestionDesTournois.Utils.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TournoiView {
    private TournoiMetierImpl tournoiService;
    private GameService gameService;
    public TournoiView(TournoiMetierImpl tournoiService,GameService gameService) {
        this.tournoiService = tournoiService;
        this.gameService = gameService;
    }

    public void tournoiMenu() {
        int choice;
        do {
            LoggerUtil.logInfo("\n--- Menu Gestion des Tournoi ---");
            LoggerUtil.logInfo("1. Ajouter une tournoi");
            LoggerUtil.logInfo("2. Mettre à jour une tournoi");
            LoggerUtil.logInfo("3. Supprimer une tournoi");
            LoggerUtil.logInfo("4. Afficher tous les tournois");
            LoggerUtil.logInfo("5. Calcul de la durée totale estimée du tournoi");
            LoggerUtil.logInfo("6. Quitter");
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
                    deleteTournoi();
                    break;
                case 4:
                    displayTournois();
                    break;
                case 5:
                    calculeDureeEstimee();
                    break;
                case 6:
                    LoggerUtil.logInfo("Quitter le menu des tournois.");
                    return;
                default:
                    LoggerUtil.logError("Option invalide, veuillez choisir entre 1 et 5.");
            }
        } while (choice != 6);
    }

    private void addTournoi() {
        LoggerUtil.logInfo("\n--- Ajouter Tournoi ---");
        LoggerUtil.logInfo("Entrez le titre du tournoi : ");
        String titre = ValidationUtil.ValidationString();
        Game game = null;
        while (game == null){
            LoggerUtil.logInfo("Entrez le ID du jeu que vous voulez ajouter à cette tournoi ");
            int gameId = ValidationUtil.validationInt();
            Optional<Game> optionalGame = gameService.getGameById(gameId);
            if (optionalGame.isPresent()){
                game = optionalGame.get();
            }else {
                LoggerUtil.logError("Le jeu n'existe pas");
            }
        }
        LocalDate dateDebut = null;
        do {
            LoggerUtil.logInfo("Entrez la date de debut pour ce tournoi (format : dd/MM/yyyy) : ");
            String date = ValidationUtil.ValidationString();
            if (DateUtil.isValidDate(date)) {
                dateDebut = DateUtil.dateValidation(date);
                break;
            } else {
                LoggerUtil.logInfo("La date est invalide. Veuillez réessayer.");
            }
        } while (true);

        LocalDate dateFin = null;
        do {
            LoggerUtil.logInfo("Entrez la date de fin pour ce tournoi (format : dd/MM/yyyy) : ");
            String date = ValidationUtil.ValidationString();
            if (DateUtil.isValidDate(date)) {
                dateFin = DateUtil.dateValidation(date);
                if (!dateFin.isBefore(dateDebut)) {
                    break;
                } else {
                    LoggerUtil.logWarn("La date de fin ne peut pas être antérieure à la date de début. Veuillez réessayer.");
                }
            } else {
                LoggerUtil.logInfo("La date est invalide. Veuillez réessayer.");
            }
        } while (true);
        LoggerUtil.logInfo("Entrez le nombres des spectateurs du tournoi : ");
        int spectateurs = ValidationUtil.validationInt();
        LoggerUtil.logInfo("Entrez la dureé estimée du tournoi : ");
        int dureEstimee = ValidationUtil.validationInt();
        LoggerUtil.logInfo("Entrez le temps de pause du tournoi : ");
        int tempsPause = ValidationUtil.validationInt();
        LoggerUtil.logInfo("Entrez le temps de cérémonie du tournoi : ");
        int tempsCeremonie = ValidationUtil.validationInt();
        LoggerUtil.logInfo("Entrez le statut de la tournoi : ");
        LoggerUtil.logInfo("1.PLANIFIE");
        LoggerUtil.logInfo("2.EN_COURS");
        LoggerUtil.logInfo("3.TERMINE");
        LoggerUtil.logInfo("4.ANNULE");
        int choice = ValidationUtil.validationInt();
        StatutTournoi statutTournoi = null;
        do {
            if (choice ==1 ){
                statutTournoi = StatutTournoi.PLANIFIE;
            } else if (choice == 2) {
                statutTournoi = StatutTournoi.EN_COURS;
            } else if (choice == 3) {
                statutTournoi = StatutTournoi.TERMINE;
            } else if (choice==4) {
                statutTournoi = StatutTournoi.ANNULE;
            }else {
                LoggerUtil.logInfo("Option invalide, veuillez choisir une option.");
            }
        }while (statutTournoi == null);

        Tournoi tournoi = new Tournoi();
        tournoi.setDateDebut(dateDebut);
        tournoi.setDateFin(dateFin);
        tournoi.setGame(game);
        tournoi.setDureeEstimee(dureEstimee);
        tournoi.setTempsPause(tempsPause);
        tournoi.setTempsCeremonie(tempsCeremonie);
        tournoi.setStatut(statutTournoi);
        tournoi.setTitle(titre);
        tournoi.setNombreSpectateurs(spectateurs);
        boolean isAdded = tournoiService.addTournoi(tournoi);
        if (isAdded) {
            LoggerUtil.logInfo("Le tournoi a été ajouter avec succès");
        }else {
            LoggerUtil.logError("Erreur lors de l'ajout du tournoi.");
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

        // Update Title
        LoggerUtil.logInfo("Entrez le nouveau titre du tournoi (actuel : " + tournoi.getTitle() + ") : ");
        String newTitle = ValidationUtil.ValidationString();
        if (!newTitle.isEmpty()) {
            tournoi.setTitle(newTitle);
        }

        // Update Game
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

        // Update DateDebut
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

        // Update DateFin
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

        // Update Estimated Duration
        LoggerUtil.logInfo("Entrez la nouvelle durée estimée (actuelle : " + tournoi.getDureeEstimee() + ") : ");
        String newDureeEstimee = ValidationUtil.ValidationString();
        if (!newDureeEstimee.isEmpty()) {
            int dureeEstimee = Integer.parseInt(newDureeEstimee);
            tournoi.setDureeEstimee(dureeEstimee);
        }

        // Update Break Time
        LoggerUtil.logInfo("Entrez le nouveau temps de pause (actuel : " + tournoi.getTempsPause() + ") : ");
        String newTempsPause = ValidationUtil.ValidationString();
        if (!newTempsPause.isEmpty()) {
            int tempsPause = Integer.parseInt(newTempsPause);
            tournoi.setTempsPause(tempsPause);
        }

        // Update Ceremony Time
        LoggerUtil.logInfo("Entrez le nouveau temps de cérémonie (actuel : " + tournoi.getTempsCeremonie() + ") : ");
        String newTempsCeremonie = ValidationUtil.ValidationString();
        if (!newTempsCeremonie.isEmpty()) {
            int tempsCeremonie = Integer.parseInt(newTempsCeremonie);
            tournoi.setTempsCeremonie(tempsCeremonie);
        }

        // Update StatutTournoi
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

        // Save Updated Tournament
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



}
