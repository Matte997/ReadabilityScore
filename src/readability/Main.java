package readability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("en", "US"));
        Scanner scanner = new Scanner(System.in);

        File file = new File(args[0]);

        String line;

        double parole = 0, sillabe = 0, lettere = 0, frasi = 0, poliSillabe = 0;

        System.out.println("The text is:");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] word = line.split(" ");
                parole += word.length;

                String[] syllables = line.split("(?i)[aeiouy]+|[\\d]+th");
                sillabe += syllables.length;

                for (String parola : word) {
                    char beforeE = parola.charAt(parola.length() - 2);

                    if (parola.split("(?i)[aeiouy]+").length > 1
                            && parola.endsWith("e")) {
                        if (beforeE == 'a' || beforeE == 'e'
                                || beforeE == 'i' || beforeE == 'o'
                                || beforeE == 'u' || beforeE == 'y') {
                            continue;
                        }
                        sillabe--;
                    }

                    if (parola.matches("[\\w]+[?.!,]")) {
                        if (beforeE == 'e') {
                            char letterBefore = parola.charAt(parola.length() - 3);

                            if (letterBefore == 'a' || letterBefore == 'e'
                                || letterBefore == 'i' || letterBefore == 'o'
                                    || letterBefore == 'u' || letterBefore == 'y') {
                                continue;
                            }
                            sillabe--;
                        }
                    }
                }
                String[] characters = line.replaceAll(" ", "").split("");
                lettere += characters.length;

                String[] sentences = line.split("[.?!]");
                frasi += sentences.length;

                for (String parola : word) {
                    Matcher match = Pattern.compile("(?i)[aeiouy]+").matcher(parola);
                    int contaSillabe = 0;

                    if (parola.split("(?i)[aeiouy]+").length > 1
                            && parola.endsWith("e")) {
                        contaSillabe--;
                    }

                    if (parola.matches("[\\w]+[?.!,]")) {
                        if (parola.charAt(parola.length() - 2) == 'e') {
                            contaSillabe--;
                        }
                    }

                    while (match.find()) {
                        contaSillabe++;
                    }

                    if (contaSillabe >= 3) {
                        poliSillabe++;
                    }
                }
                System.out.println(line);
            }
        }

        System.out.println("\nWords: " + (int)parole + "\nSentences: " + (int)frasi + "\nCharacters: " + (int) lettere
                + "\nSyllables: " + (int)sillabe + "\nPolysyllables: " + (int)poliSillabe);

        double indiceARI = 4.71 * (lettere / parole) + 0.5 * (parole / frasi) - 21.43;
        double indiceFK = 0.39 * (parole / frasi) + 11.8 * (sillabe / parole) - 15.59;
        double indiceSMOG = 1.0430 * Math.sqrt(poliSillabe * (30 / frasi)) + 3.1291;
        double indiceCL = 0.0588 * (lettere / parole * 100) - 0.296 * (frasi / parole * 100) - 15.8;

        String[] eta = {"null", "6", "7", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "24", "24+"};

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):  ");

        DecimalFormat df = new DecimalFormat(".00");

        String choice = scanner.next();

        if (choice.equalsIgnoreCase("ARI")) {
            System.out.println("\nAutomated Readability Index: " + df.format(indiceARI) + " (about " + eta[(int) indiceARI] + " year olds).");
        } else if (choice.equalsIgnoreCase("FK")) {
            System.out.println("\nFlesch\u2013Kincaid readability tests: " + df.format(indiceFK)+ " (about " + eta[(int) indiceFK] + " year olds).");
        } else if (choice.equalsIgnoreCase("SMOG")) {
            System.out.println("\nSimple Measure of Gobbledygook: " + df.format(indiceSMOG) + " (about " + eta[(int) indiceSMOG] + " year olds).");
        } else if (choice.equalsIgnoreCase("CL")) {
            System.out.println("\nColeman\u2013Liau index: " + df.format(indiceCL) + " (about " + eta[(int) indiceCL] + " year olds).");
        } else if (choice.equalsIgnoreCase("all")) {
            System.out.println("\nAutomated Readability Index: " + df.format(indiceARI) + " (about " + eta[(int) indiceARI] + " year olds).");
            System.out.println("Flesch\u2013Kincaid readability tests: " + df.format(indiceFK) + " (about " + eta[(int) indiceFK] + " year olds).");
            System.out.println("Simple Measure of Gobbledygook: " + df.format(indiceSMOG) + " (about " + eta[(int) indiceSMOG] + " year olds).");
            System.out.println("Coleman\u2013Liau index: " + df.format(indiceCL) + " (about " + eta[(int) indiceCL] + " year olds).");
        }

        eta[eta.length - 1] = "25";

        double mediaIndici = (Integer.parseInt(eta[(int) indiceARI]) + Integer.parseInt(eta[(int) indiceCL])
                + Integer.parseInt(eta[(int) indiceFK]) + Integer.parseInt(eta[(int) indiceSMOG]));

        System.out.println("\nThis text should be understood in average by " + mediaIndici / 4 + " year olds.");
    }
}
