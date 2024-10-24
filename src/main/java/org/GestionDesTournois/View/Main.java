package org.GestionDesTournois.View;


import org.GestionDesTournois.Utils.LoggerUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LoggerUtil.logInfo("Application context loaded");
//        GameView gameView = context.getBean("gameView", GameView.class);
//        gameView.displayMenu();
//        TournoiView tournoiView = context.getBean("tournoiView", TournoiView.class);
//        tournoiView.tournoiMenu();
          TeamView teamView = context.getBean("teamView", TeamView.class);
          teamView.displayMenu();


    }
}