package org.GestionDesTournois.Utils;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ValidationUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static String ValidationString() {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Erreur : vous devez entrer une chaîne de caractères non vide : ");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static int validationInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                if (value <= 0) {
                    LoggerUtil.logWarn("Veuillez entrer un nombre entier supérieur à zéro.");
                }
                scanner.nextLine();
                return  value;
            } catch (InputMismatchException e) {
                LoggerUtil.logWarn("Veuillez entrer un nombre entier valide.");
                scanner.next();
            }
        }
    }

    public static double validationDouble() {
        scanner.useLocale(Locale.US);
        while (true) {
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } else {
                LoggerUtil.logWarn("Erreur : veuillez entrer un nombre valide.");
                scanner.next();
            }
        }
    }

    private static boolean isValidName(String input) {
        return input != null && input.matches("[a-zA-Z\\s]+");
    }

    public static String ValidationName() {
        String input = scanner.next();
        if (!isValidName(input)) {
            LoggerUtil.logWarn("Erreur : le nom ne doit contenir que des lettres. Veuillez réessayer.");
            return ValidationName();
        }
        return input;
    }

}
