package org.GestionDesTournois.View;

import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Models.Tournoi;
import org.GestionDesTournois.Service.TeamService;
import org.GestionDesTournois.Service.TournoiMetierImpl;
import org.GestionDesTournois.Utils.LoggerUtil;
import org.GestionDesTournois.Utils.ValidationUtil;

import java.util.List;
import java.util.Optional;

public class TeamView {
    private final TeamService teamService;
    private final TournoiMetierImpl tournoiMetier;

    public TeamView(TeamService teamService,TournoiMetierImpl tournoiMetier){
        this.teamService=teamService;
        this.tournoiMetier = tournoiMetier;
    }

    public void displayMenu() {
        while (true) {
            LoggerUtil.logInfo("\n--- Menu Équipe ---");
            LoggerUtil.logInfo("1. Ajouter une équipe");
            LoggerUtil.logInfo("2. Mettre à jour une équipe");
            LoggerUtil.logInfo("3. Supprimer une équipe");
            LoggerUtil.logInfo("4. Afficher toutes les équipes");
            LoggerUtil.logInfo("5. Quitter");

            LoggerUtil.logInfo("Veuillez sélectionner une option : ");
            int choix = ValidationUtil.validationInt();

            switch (choix) {
                case 1:
                    addTeam();
                    break;
                case 2:
                    updateTeam();
                    break;
                case 3:
                    deleteTeam();
                    break;
                case 4:
                    displayAllTeams();
                    break;
                case 5:
                    LoggerUtil.logInfo("Quitter le menu des équipes");
                    return;
                default:
                    LoggerUtil.logError("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void addTeam() {
        LoggerUtil.logInfo("\n--- Ajouter une équipe ---");
        LoggerUtil.logInfo("Entrez le nom de l'équipe : ");
        String nom = ValidationUtil.ValidationString();
        LoggerUtil.logInfo("Entrez le classement de l'équipe  : ");
        int classement = ValidationUtil.validationInt();
        Tournoi tournoi = null;
        while (tournoi == null){
            LoggerUtil.logInfo("Entrez le id du tournoi ou l'équipe se jouer : ");
            int tournoiId = ValidationUtil.validationInt();
            Optional<Tournoi> tournoiOptional = tournoiMetier.getTournoiById(tournoiId);
            if (tournoiOptional.isPresent()){
                tournoi = tournoiOptional.get();
            }else {
                LoggerUtil.logError("Tournoi introuvable. Veuillez réessayer avec un ID valide.");
            }
        }

        Team team = new Team();
        team.setNom(nom);
        team.setClassement(classement);
        team.setTournoi(tournoi);

        boolean isAdded = teamService.addTeam(team);
        if (isAdded) {
            LoggerUtil.logInfo("L'équipe a été ajoutée avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de l'ajout de l'équipe.");
        }
    }

    private void updateTeam() {
        LoggerUtil.logInfo("\n--- Mettre à jour une équipe ---");
        LoggerUtil.logInfo("Entrez l'ID de l'équipe à mettre à jour : ");
        int teamId = ValidationUtil.validationInt();

        Optional<Team> optionalTeam = teamService.getTeamById(teamId);
        if (optionalTeam.isEmpty()) {
            LoggerUtil.logError("Équipe introuvable avec l'ID donné.");
            return;
        }
        Team existingTeam = optionalTeam.get();
        LoggerUtil.logInfo("Entrez le nouveau nom de l'équipe (actuel : " + existingTeam.getNom() + ") : ");
        String newNom = ValidationUtil.ValidationString();
        if (!newNom.trim().isEmpty()) {
            existingTeam.setNom(newNom);
        }

        LoggerUtil.logInfo("Entrez le nouveau classement de l'équipe (actuel : " + existingTeam.getClassement() + ") : ");
        String newClassementInput = ValidationUtil.ValidationString();
        if (!newClassementInput.trim().isEmpty()) {
                int newClassement = Integer.parseInt(newClassementInput);
                existingTeam.setClassement(newClassement);
            }


        LoggerUtil.logInfo("Voulez-vous modifier le tournoi associé à cette équipe ? (O/N) : ");
        String reponse = ValidationUtil.ValidationString();
        if (reponse.equalsIgnoreCase("O")) {
            while (true) {
                LoggerUtil.logInfo("Entrez l'ID du tournoi auquel vous souhaitez associer l'équipe (laisser vide pour conserver l'ID actuel) : ");
                String tournoiIdInput = ValidationUtil.ValidationString();

                if (tournoiIdInput.trim().isEmpty()) {
                    break;
                }

                int tournoiId = Integer.parseInt(tournoiIdInput);
                Optional<Tournoi> tournoiOptional = tournoiMetier.getTournoiById(tournoiId);
                if (tournoiOptional.isPresent()) {
                    existingTeam.setTournoi(tournoiOptional.get());
                        break;
                    } else {
                        LoggerUtil.logError("Tournoi introuvable. Veuillez réessayer avec un ID valide.");
                    }

            }
        }

        boolean isUpdated = teamService.modifyTeam(existingTeam);
        if (isUpdated) {
            LoggerUtil.logInfo("L'équipe a été mise à jour avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de la mise à jour de l'équipe.");
        }
    }


    private void deleteTeam() {
        LoggerUtil.logInfo("\n--- Supprimer une équipe ---");
        LoggerUtil.logInfo("Entrez l'ID de l'équipe à supprimer : ");
        int teamId = ValidationUtil.validationInt();

        boolean isDeleted = teamService.removeTeam(teamId);
        if (isDeleted) {
            LoggerUtil.logInfo("L'équipe a été supprimée avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de la suppression de l'équipe ou équipe introuvable.");
        }
    }

    private void displayAllTeams() {
        LoggerUtil.logInfo("\n--- Afficher toutes les équipes ---");
        List<Team> teams = teamService.getAllTeams();

        if (teams.isEmpty()) {
            LoggerUtil.logInfo("Aucune équipe trouvée.");
        } else {
            teams.forEach(System.out::println);
        }
    }
}
