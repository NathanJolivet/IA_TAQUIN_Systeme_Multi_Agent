package com.company;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

public class Grille {
    private Grille grilleFinale;
    private ArrayList<ArrayList<Case>> grille = new ArrayList<ArrayList<Case>>();
    private ArrayList<Agent> agents = new ArrayList<Agent>();
    int taille;

    public Grille(int taille){
        this.taille = taille;
        for(int i = 0; i < taille; i++){
            ArrayList<Case> ligne_i = new ArrayList<Case>();
            for(int colonne = 0; colonne < taille; colonne++){
                Case caze = new Case(this, new Position(i, colonne));
                ligne_i.add(caze);
            }
            grille.add(ligne_i);
        }
    }

    //Pour recuperer toutes les cases adjacentes
    public ArrayList<Position> positionsCasesAdjacentes(Case caze){
        ArrayList<Position> casesAdjacentes = new ArrayList<Position>();
        for(int ligne = 0; ligne < taille; ligne++){
            for(int colonne = 0; colonne < taille; colonne++){
                if(caze.distanceTo(grille.get(ligne).get(colonne)) == 1){
                    int x = grille.get(ligne).get(colonne).getPosition().getX();
                    int y = grille.get(ligne).get(colonne).getPosition().getY();
                    casesAdjacentes.add(new Position(x, y));
                }
            }
        }
        return casesAdjacentes;
    }

    /////////////////////////////////////////// INITIALISATION DE LA GRILLE ///////////////////////////////////////////

    public void initGrille(int nbAgents){
        this.placerAgents(nbAgents);
        this.grilleFinale();
    }


    public void placerAgents(int nbAgents){
        Random rdm = new Random();
        int i = 0;
        while(i < nbAgents){
            int ligneRdm = rdm.nextInt(taille);
            int colonneRdm = rdm.nextInt(taille);
            if(grille.get(ligneRdm).get(colonneRdm).isVide()){

                Agent agent = new Agent(i+1, this);
                agent.setPositionActuelle(grille.get(ligneRdm).get(colonneRdm).getPosition());
                agents.add(agent);
                //agent.run();

                grille.get(ligneRdm).get(colonneRdm).setAgent(agent);

                i++;
            }
        }
    }


    public void grilleFinale(){

        Grille grilleFinale = new Grille(taille);
        Random rdm = new Random();

        for(int i = 0; i < grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                if (!grille.get(i).get(j).isVide()) {
                    int n = 0;
                    while (n < 1) {
                        int ligneRdm = rdm.nextInt(grilleFinale.getGrille().size());
                        int colonneRdm = rdm.nextInt(grilleFinale.getGrille().get(ligneRdm).size());
                        if (grilleFinale.getGrille().get(ligneRdm).get(colonneRdm).isVide()) {

                            grille.get(i).get(j).getAgent().setPositionFinale(grilleFinale.getGrille().get(ligneRdm).get(colonneRdm).getPosition());
                            grilleFinale.getAgents().add(grille.get(i).get(j).getAgent());

                            grilleFinale.getGrille().get(ligneRdm).get(colonneRdm).setAgent(grille.get(i).get(j).getAgent());

                            n++;
                        }
                    }
                }
            }

        }

        this.grilleFinale = grilleFinale;

        for(int x = 0; x < agents.size(); x++){
            agents.get(x).setGrilleFinale(grilleFinale);
        }
    }


    /////////////////////////////////////////// TEST FIN ///////////////////////////////////////////

    public boolean testFin(){

        for(int i = 0; i < agents.size(); i++){
            if(agents.get(i).getPositionActuelle().getX() != agents.get(i).getPositionFinale().getX()
                    || agents.get(i).getPositionActuelle().getY() != agents.get(i).getPositionFinale().getY()){
                return false;
            }
        }
        return true;
    }

    /////////////////////////////////////////// GETTER AND SETTER ///////////////////////////////////////////


    public ArrayList<ArrayList<Case>> getGrille() {
        return grille;
    }

    public void setGrille(ArrayList<ArrayList<Case>> grille) {
        this.grille = grille;
    }

    public Grille getGrilleFinale() {
        return grilleFinale;
    }

    public void setGrilleFinale(Grille grilleFinale) {
        this.grilleFinale = grilleFinale;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public int getTaille() {
        return taille;
    }

    /////////////////////////////////////////// AFFICHAGE ///////////////////////////////////////////
    public String toString(){
        String affGrille = "";
        for(int i = 0; i < taille; i++){
            for(int j = 0; j < taille; j++){
                affGrille += grille.get(i).get(j).toString();
            }
            affGrille += "\n";
        }
        return affGrille;
    }

}
