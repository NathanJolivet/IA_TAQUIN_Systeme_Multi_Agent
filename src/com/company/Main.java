package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        Grille grille;
        int taille;
        do {
            System.out.println("Quelle est la taille n du puzzle souhaitée ? (grille en n*n)");
            taille = sc.nextInt();
            grille = new Grille(taille);
        }while(taille <= 1);

        int nbAgents;
        do {
            System.out.println("Combien de pièce voulez vous avoir dans votre puzzle ?");
            nbAgents = sc.nextInt();
            grille.initGrille(nbAgents);
        }while(nbAgents <= 0 || nbAgents > (taille*taille)-1);

        long debut = System.currentTimeMillis();
        while(!grille.testFin()) {
            for (int i = 0; i < grille.getAgents().size(); i++) {

                grille.getAgents().get(i).action(grille.getAgents().get(i).choixDestination());
                System.out.println("grilleActuelle\n Agent " + (i+1) + "\n" + grille.getAgents().get(i).getGrilleActuelle().toString());
                //Thread.sleep(500);


            }
            System.out.println("grilleFinale\n" + grille.getAgents().get(0).getGrilleFinale().toString());

        }
        long fin = System.currentTimeMillis();
        System.out.println("Le résultat à été obtenu en: " + (fin - debut) + " ms");

    }
}
