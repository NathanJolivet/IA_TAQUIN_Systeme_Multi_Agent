package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        long debut = System.currentTimeMillis();

        Scanner sc = new Scanner(System.in);
        int taille;
        do {
            System.out.println("Quelle est la taille du puzzle souhaitée ?");
            taille = sc.nextInt();
        }while(taille <= 1);

        int nbAgents;
        do {
            System.out.println("Vous voulez avoir combien de pièce dans votre puzzle ?");
            nbAgents = sc.nextInt();
        }while(nbAgents <= 0 || nbAgents > (taille*taille)-1);

        Grille grille = new Grille(taille);
        grille.initGrille(nbAgents);

        while(!grille.testFin()) {
            for (int i = 0; i < grille.getAgents().size(); i++) {

                grille.getAgents().get(i).action(grille.getAgents().get(i).choixDestination());
                System.out.println("grilleActuelle\n Agent " + (i+1) + "\n" + grille.getAgents().get(i).getGrilleActuelle().toString());
                //Thread.sleep(1000);
            }
            System.out.println("grilleFinale\n" + grille.getAgents().get(0).getGrilleFinale().toString());

        }

        long fin = System.currentTimeMillis();
        System.out.println("Le résultat à été obtenu en: " + (fin - debut) + " ms");
    }
}
