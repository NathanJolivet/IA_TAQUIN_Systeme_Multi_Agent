package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        long debut = System.currentTimeMillis();

        Grille grille = new Grille(3);
        grille.initGrille(4);

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
