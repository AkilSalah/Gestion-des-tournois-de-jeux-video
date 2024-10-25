package org.GestionDesTournois.View;

import org.GestionDesTournois.Models.Game;
import org.GestionDesTournois.Service.GameService;
import org.GestionDesTournois.Utils.LoggerUtil;
import org.GestionDesTournois.Utils.ValidationUtil;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GameView {
    private final GameService gameService;
    public GameView(GameService gameService){
    this.gameService = gameService;
    }
    Scanner scanner = new Scanner(System.in);
    public void gameMenu() {
        int choice;
        do {
            LoggerUtil.logInfo("\n--- Gestion des Jeux ---");
            LoggerUtil.logInfo("1. Ajouter un jeu");
            LoggerUtil.logInfo("2. Mettre à jour un jeu");
            LoggerUtil.logInfo("3. Supprimer un jeu");
            LoggerUtil.logInfo("4. Afficher tous les jeux");
            LoggerUtil.logInfo("5. Quitter");
            LoggerUtil.logInfo("Choisissez une option : ");

            choice = ValidationUtil.validationInt();
            switch (choice) {
                case 1:
                    addGame();
                    break;
                case 2:
                    updateGame();
                    break;
                case 3:
                    deleteGame();
                    break;
                case 4:
                    displayAllGames();
                    break;
                case 5:
                    LoggerUtil.logInfo("Quitter le menu des jeux");
                    return;
                default:
                    LoggerUtil.logInfo("Option invalide, veuillez réessayer.");
            }
        } while (choice != 5);
    }
    public void addGame() {
        LoggerUtil.logInfo("\n--- Ajouter un jeu ---");
        LoggerUtil.logInfo("Entrez le nom du jeu : ");
        String name = ValidationUtil.ValidationString();
        int difficulte;
        do {
            LoggerUtil.logInfo("Entrez la difficulté du jeu :");
            LoggerUtil.logInfo("1. Facile");
            LoggerUtil.logInfo("2. Moyen");
            LoggerUtil.logInfo("3. Difficile");
            difficulte = ValidationUtil.validationInt();
            if (difficulte < 1 || difficulte > 3) {
                LoggerUtil.logError("Valeur incorrecte. Veuillez saisir 1, 2 ou 3.");
            }
        } while (difficulte < 1 || difficulte > 3);
        LoggerUtil.logInfo("Entrez la durée moyenne du match (en minutes) : ");
        int duree = ValidationUtil.validationInt();
        Game game = new Game();
        game.setNom(name);
        game.setDifficulte(difficulte);
        game.setDureeMoyenneMatch(duree);
        boolean isAdded = gameService.addGame(game);
        if (isAdded) {
            LoggerUtil.logInfo("Le jeu a été ajouté avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de l'ajout du jeu.");
        }
    }
    public void updateGame() {
        LoggerUtil.logInfo("Entrez l'ID du jeu à mettre à jour : ");
        int id = ValidationUtil.validationInt();
        Optional<Game> gameOptional = gameService.getGameById(id);
        if (!gameOptional.isPresent()) {
            LoggerUtil.logError("Jeu introuvable avec l'ID fourni.");
            return;
        }

        Game game = gameOptional.get();
        LoggerUtil.logInfo("\n--- Mise à jour du jeu ---");
        LoggerUtil.logInfo("Nom actuel du jeu : " + game.getNom());
        LoggerUtil.logInfo("Entrez le nouveau nom du jeu (laisser vide pour conserver le nom actuel) : ");
        String newName = ValidationUtil.ValidationString();
        if (!newName.trim().isEmpty()) {
            game.setNom(newName);
        }
        int newDifficulte;
        do {
            LoggerUtil.logInfo("Difficulté actuelle : " + game.getDifficulte());
            LoggerUtil.logInfo("Entrez la nouvelle difficulté (1. Facile, 2. Moyen, 3. Difficile) ou laissez vide pour conserver la difficulté actuelle : ");
            String difficulteInput = ValidationUtil.ValidationString();
            if (difficulteInput.trim().isEmpty()) {
                newDifficulte = game.getDifficulte();
                break;
            } else {
                try {
                    newDifficulte = Integer.parseInt(difficulteInput);
                    if (newDifficulte < 1 || newDifficulte > 3) {
                        LoggerUtil.logError("Valeur incorrecte. Veuillez saisir 1, 2 ou 3.");
                        newDifficulte = -1;
                    }
                } catch (NumberFormatException e) {
                    LoggerUtil.logError("Entrée invalide. Veuillez entrer un nombre entre 1 et 3.");
                    newDifficulte = -1;
                }
            }
        } while (newDifficulte < 1 || newDifficulte > 3);

        game.setDifficulte(newDifficulte);
        LoggerUtil.logInfo("Durée moyenne actuelle du match : " + game.getDureeMoyenneMatch() + " minutes");
        LoggerUtil.logInfo("Entrez la nouvelle durée moyenne du match (laisser vide pour conserver la durée actuelle) : ");
        String dureeInput = ValidationUtil.ValidationString();
        if (!dureeInput.trim().isEmpty()) {
            try {
                int newDuree = Integer.parseInt(dureeInput);
                game.setDureeMoyenneMatch(newDuree);
            } catch (NumberFormatException e) {
                LoggerUtil.logError("Entrée invalide. La durée doit être un nombre entier.");
            }
        }
        boolean isUpdated = gameService.modifyGame(game);
        if (isUpdated) {
            LoggerUtil.logInfo("Le jeu a été mis à jour avec succès !");
        } else {
            LoggerUtil.logError("Erreur lors de la mise à jour du jeu.");
        }
    }
    public void deleteGame() {
        LoggerUtil.logInfo("\n--- Supprimer un jeu ---");
        LoggerUtil.logInfo("Entrez l'ID du jeu à supprimer : ");
        int id = ValidationUtil.validationInt();

        boolean isDeleted = gameService.removeGame(id);
        if (isDeleted) {
            LoggerUtil.logInfo("Le jeu a été supprimé avec succès !");
        } else {
            LoggerUtil.logInfo("Erreur lors de la suppression du jeu.");
        }
    }

    public void displayAllGames(){
        LoggerUtil.logInfo("Liste des jeux : ");
        List<Game> games = gameService.getAllGames();
        if (games.isEmpty()){
            LoggerUtil.logInfo("Aucun jeu trouvé");
        }else {
            games.forEach(game -> LoggerUtil.logInfo(game.toString()));
        }
    }
}
