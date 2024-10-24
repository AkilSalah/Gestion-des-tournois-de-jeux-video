package org.GestionDesTournois.View;


import org.GestionDesTournois.Utils.LoggerUtil;
import org.GestionDesTournois.Utils.ValidationUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        GameView gameView = context.getBean("gameView", GameView.class);
        TournoiView tournoiView = context.getBean("tournoiView", TournoiView.class);
        TeamView teamView = context.getBean("teamView", TeamView.class);
        PlayerView playerView = context.getBean("playerView",PlayerView.class);
        int choice;
        do {
            LoggerUtil.logInfo("\n--- Menu Gestion des tournois de jeux vidéo ---");
            LoggerUtil.logInfo("1. Gestion des jeux");
            LoggerUtil.logInfo("2. Gestion des tournois");
            LoggerUtil.logInfo("3. Gestion des équipes");
            LoggerUtil.logInfo("4. Gestion des joueurs");
            LoggerUtil.logInfo("5. Quitter");
            LoggerUtil.logInfo("Choisissez une option : ");

            choice = ValidationUtil.validationInt();

            switch (choice) {
                case 1:
                    gameView.gameMenu();
                    break;
                case 2:
                    tournoiView.tournoiMenu();
                    break;
                case 3:
                    teamView.teamMenu();
                    break;
                case 4:
                    playerView.playerMenu();
                    break;
                case 5:
                    LoggerUtil.logInfo("Au Revoir .");
                    return;
                default:
                    LoggerUtil.logError("Option invalide, veuillez choisir entre 1 et 5.");
            }
        } while (choice != 5);
    }



}