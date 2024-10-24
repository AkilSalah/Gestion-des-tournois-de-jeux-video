package org.GestionDesTournois.View;

import org.GestionDesTournois.Models.Player;
import org.GestionDesTournois.Models.Team;
import org.GestionDesTournois.Service.PlayerService;
import org.GestionDesTournois.Service.TeamService;
import org.GestionDesTournois.Utils.LoggerUtil;
import org.GestionDesTournois.Utils.ValidationUtil;

import java.util.List;
import java.util.Optional;

public class PlayerView {

    private final PlayerService playerService ;
    private final TeamService teamService;
    public PlayerView(PlayerService playerService,TeamService teamService){
        this.playerService = playerService;
        this.teamService =teamService;
    }
    public void playerMenu() {
        int choice;
        do {
            LoggerUtil.logInfo("\n--- Menu Gestion des Joueurs ---");
            LoggerUtil.logInfo("1. Ajouter un joueur");
            LoggerUtil.logInfo("2. Mettre à jour un joueur");
            LoggerUtil.logInfo("3. Supprimer un joueur");
            LoggerUtil.logInfo("4. Afficher tous les joueurs");
            LoggerUtil.logInfo("5. Quitter");
            LoggerUtil.logInfo("Choisissez une option : ");

            choice = ValidationUtil.validationInt();

            switch (choice) {
                case 1:
                    addPlayer();
                    break;
                case 2:
                    updatePlayer();
                    break;
                case 3:
                    deletePlayer();
                    break;
                case 4:
                    displayAllPlayers();
                    break;
                case 5:
                    LoggerUtil.logInfo("Quitter le menu des joueurs.");
                    return;
                default:
                    LoggerUtil.logError("Option invalide, veuillez choisir entre 1 et 5.");
            }
        } while (choice != 5);
    }

    public void addPlayer() {
        LoggerUtil.logInfo("\n--- Ajouter un joueur ---");

        LoggerUtil.logInfo("Entrez le nom du joueur : ");
        String pseudo = ValidationUtil.ValidationString();

        LoggerUtil.logInfo("Entrez l'âge du joueur : ");
        int age = ValidationUtil.validationInt();

        Team equipe = null;
        while (equipe == null) {
            LoggerUtil.logInfo("Entrez l'ID de l'équipe du joueur : ");
            int equipeId = ValidationUtil.validationInt();

            Optional<Team> optionalEquipe = teamService.getTeamById(equipeId);
            if (optionalEquipe.isPresent()) {
                equipe = optionalEquipe.get();
            } else {
                LoggerUtil.logError("Équipe introuvable. Veuillez entrer un ID d'équipe valide.");
            }
        }
        Player player = new Player();
        player.setPseudo(pseudo);
        player.setAge(age);
        player.setEquipe(equipe);

        boolean isAdded = playerService.addPlayer(player);
        if (isAdded) {
            LoggerUtil.logInfo("Le joueur a été ajouté avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de l'ajout du joueur.");
        }
    }


    public void updatePlayer() {
        LoggerUtil.logInfo("\n--- Mettre à jour un joueur ---");

        LoggerUtil.logInfo("Entrez l'ID du joueur à mettre à jour : ");
        int playerId = ValidationUtil.validationInt();

        Optional<Player> optionalPlayer = playerService.getPlayerById(playerId);
        if (optionalPlayer.isEmpty()) {
            LoggerUtil.logError("Joueur introuvable avec l'ID donné.");
            return;
        }
        Player existingPlayer = optionalPlayer.get();

        LoggerUtil.logInfo("Entrez le nouveau pseudo du joueur (actuel : " + existingPlayer.getPseudo() + ") : ");
        String newPseudo = ValidationUtil.ValidationString();
        if (!newPseudo.isEmpty()) {
            existingPlayer.setPseudo(newPseudo);
        }

        LoggerUtil.logInfo("Entrez le nouvel âge du joueur (actuel : " + existingPlayer.getAge() + ") : ");
        String ageInput = ValidationUtil.ValidationString();
        if (!ageInput.isEmpty()) {
            int newAge = Integer.parseInt(ageInput);
            existingPlayer.setAge(newAge);
        }

        Team newEquipe = null;
        while (newEquipe == null) {
            LoggerUtil.logInfo("Entrez l'ID de la nouvelle équipe (actuelle : " +
                    (existingPlayer.getEquipe() != null ? existingPlayer.getEquipe().getId() : "Aucune") + ") : ");
            String equipeInput = ValidationUtil.ValidationString();
            if (!equipeInput.isEmpty()) {
                int equipeId = Integer.parseInt(equipeInput);
                Optional<Team> optionalEquipe = teamService.getTeamById(equipeId);
                if (optionalEquipe.isPresent()) {
                    newEquipe = optionalEquipe.get();
                } else {
                    LoggerUtil.logError("Équipe introuvable. Veuillez entrer un ID d'équipe valide.");
                }
            } else {
                newEquipe = existingPlayer.getEquipe();
            }
        }

        existingPlayer.setEquipe(newEquipe);
        boolean isUpdated = playerService.modifyPlayer(existingPlayer);
        if (isUpdated) {
            LoggerUtil.logInfo("Le joueur a été mis à jour avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de la mise à jour du joueur.");
        }
    }


    public void deletePlayer() {
        LoggerUtil.logInfo("\n--- Supprimer un joueur ---");
        LoggerUtil.logInfo("Entrez l'ID du joueur à supprimer : ");
        int playerId = ValidationUtil.validationInt();

        boolean isDeleted = playerService.removePlayer(playerId);
        if (isDeleted) {
            LoggerUtil.logInfo("Le joueur a été supprimé avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de la suppression du joueur.");
        }
    }

    public void displayAllPlayers() {
        LoggerUtil.logInfo("\n--- Liste de tous les joueurs ---");

        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            LoggerUtil.logInfo("Aucun joueur trouvé.");
        } else {
          players.forEach(System.out::println);
        }
    }

}
