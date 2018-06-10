package com.company;

public class Main {

    /*TODO
   TODO - PROBLEME QUAND 2 AGENTS SONT DANS UN COIN ET SONT L'UN A LA PLACE DE L'AUTRE =>il se demande de bouger mutuellement mais n'ont qu'un seul endroit ou aller quand il bouge => boucle infini
            done    => donner la priorite lors du choix de la destination a une case qui est vide si elle existe (dans choixDestination)
   TODO             => donner la prio lors du choix a une case qui n'est pas dans un coin (fonction isDansCoin() dans Case)
   TODO             => placer en priorite les cases dans les bords
   TODO             => Compteur du nombre de demande consecutive sur la meme case, au bout de temps de fois, on ne peut plus demander cette case, puis compteur repasse a 0


   done - Disparition parfois lors d'une demande de deplacement: prend la place de l'autre donc le dernier mouvement supprime le premier qui est allé a sa place (etrange) => problème dans run: on remove donc problème d'indice

     */
    public static void main(String[] args) throws InterruptedException {

        Grille grille = new Grille(5);
        grille.initGrille(24);

        System.out.println("GrilleActuelle\n" + grille.toString());
        System.out.println("grilleFinale\n" + grille.getGrilleFinale().toString());

        int n = 0;
        while(!grille.testFin()) {
            for (int i = 0; i < grille.getAgents().size(); i++) {

                //System.out.println("positionActuelle: " + grille.getAgents().get(i).getPositionActuelle().getX() + ";" + grille.getAgents().get(i).getPositionActuelle().getY());
                //System.out.println("positionFinale: " + grille.getAgents().get(i).getPositionFinale().getX() + ";" + grille.getAgents().get(i).getPositionFinale().getY());
                grille.getAgents().get(i).action(grille.getAgents().get(i).choixDestination());
                System.out.println("grilleActuelle\n Agent " + i + "\n" + grille.getAgents().get(i).getGrilleActuelle().toString());
                //Thread.sleep(1000);
            }
            System.out.println("grilleFinale\n" + grille.getAgents().get(0).getGrilleFinale().toString());
            //n++;

        }

    }
}
