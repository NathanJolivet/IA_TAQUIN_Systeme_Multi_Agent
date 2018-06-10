package com.company;


import com.communication.*;

import java.util.ArrayList;
import java.util.Random;

import static com.communication.Message.MOVE;
import static com.communication.Message.MOVE_IMPOSSIBLE;

public class Agent{
    private Position positionActuelle;
    private Position positionFinale;
    private int index;
    private Grille grilleActuelle;
    private Grille grilleFinale;
    private Messagerie messagerie = new Messagerie();
    private boolean messageRecu = false; // Quand l'agent a recu un message pour entrer dans la methode "action" meme si il est a sa place finale
    private boolean inTransaction = false; //Quand l'agent a deja envoye une demande a un autre agent de se deplacer car il ne pouvait pas avancer
    private Agent destinataireDernierMessage = this;
    private int compteurDemandeConsecutive =  0;


    public Agent(int index, Grille grilleActuelle){
        this.index = index;
        this.grilleActuelle = grilleActuelle;
    }


    /////////////////////////////////////////// DEPLACEMENT ///////////////////////////////////////////

    public Case choixDestination(){
        ArrayList<Position> casesAdjacentes = grilleActuelle.positionsCasesAdjacentes(grilleActuelle.getGrille().get(positionActuelle.getX()).get(positionActuelle.getY()));
        int index = 0;

        //On cherche la case la plus proche de la position finale
        double distanceMin = grilleActuelle.getGrille().get(casesAdjacentes.get(0).getX()).get(casesAdjacentes.get(0).getY())
                .distanceTo(grilleActuelle.getGrille().get(positionFinale.getX()).get(positionFinale.getY()));

        // Pour donner la priorite aux cases vides si 2 cases ont la meme distance de la position finale
        boolean prioCaseVide = false;

        if(compteurDemandeConsecutive > 3){
            for(int i = 0; i < casesAdjacentes.size(); i++){
                if(casesAdjacentes.get(i).getX() == destinataireDernierMessage.getPositionActuelle().getX()
                        && casesAdjacentes.get(i).getY() == destinataireDernierMessage.getPositionActuelle().getY()){
                    casesAdjacentes.remove(i);
                    break;
                }
            }
        }

        //On parcourt toutes les cases adjacentes
        for(int i = 1; i < casesAdjacentes.size(); i++){

            //Si la distance est plus petite ou egale a celle actuelle et que la case est vide
            if(distanceMin >= grilleActuelle.getGrille().get(casesAdjacentes.get(i).getX()).get(casesAdjacentes.get(i).getY())
                    .distanceTo(grilleActuelle.getGrille().get(positionFinale.getX()).get(positionFinale.getY()))
                    && grilleActuelle.getGrille().get(casesAdjacentes.get(i).getX()).get(casesAdjacentes.get(i).getY()).isVide()){

                index = i;
                distanceMin = grilleActuelle.getGrille().get(casesAdjacentes.get(i).getX()).get(casesAdjacentes.get(i).getY())
                        .distanceTo(grilleActuelle.getGrille().get(positionFinale.getX()).get(positionFinale.getY()));
            }

            //Si distance strictement plus petite
            else{
                if(distanceMin > grilleActuelle.getGrille().get(casesAdjacentes.get(i).getX()).get(casesAdjacentes.get(i).getY())
                        .distanceTo(grilleActuelle.getGrille().get(positionFinale.getX()).get(positionFinale.getY()))){

                    index = i;
                    distanceMin = grilleActuelle.getGrille().get(casesAdjacentes.get(i).getX()).get(casesAdjacentes.get(i).getY())
                            .distanceTo(grilleActuelle.getGrille().get(positionFinale.getX()).get(positionFinale.getY()));

                }
            }

        }
        return grilleActuelle.getGrille().get(casesAdjacentes.get(index).getX()).get(casesAdjacentes.get(index).getY());

    }

    public void move(Case caseSuivante){
        //On recupère les coordonnées de la case actuelle
        int x = positionActuelle.getX();
        int y = positionActuelle.getY();

        //On reserve la case
        caseSuivante.setReservee(true);

        //On deplace l'agent sur la case et on modifie sa position
        caseSuivante.setAgent(this);
        this.setPositionActuelle(caseSuivante.getPosition());

        //On reinitialise la case precedente
        grilleActuelle.getGrille().get(x).get(y).setAgent(null);

        //On enleve la reservation
        caseSuivante.setReservee(false);
    }

    public void action(Case caseSuivante){
        //On effectue l'action seulement si on est pas a la position finale, ou si on a recu un message pour se deplacer
        if(positionActuelle.getX() != positionFinale.getX() || positionActuelle.getY() != positionFinale.getY() || messageRecu) {

            //Cas ou la case est libre
            if (caseSuivante.isVide() && !caseSuivante.isReservee()) {
                this.move(caseSuivante);
            }

            //Cas ou la case est occupee
            else {
                if (!caseSuivante.isVide()) {
                    messagerie.sendMessage(this, caseSuivante.getAgent(), MOVE);
                    caseSuivante.getAgent().run();
                    if(caseSuivante.isVide()){
                        this.move(caseSuivante);
                    }
                }
            }

            //Si la case est reservee, on ne fait rien

        }

    }

    /////////////////////////////////////////// GESTION MESSAGERIE ///////////////////////////////////////////

    //Pour supprimer l'emetteur de la liste des cases adjacentes
    public static void supprimerEmetteur(ArrayList<Position> positions, Message message){
        for(int i = 0; i < positions.size(); i++){
            if (positions.get(i).getX() == message.getEmetteur().getPositionActuelle().getX()
                    && positions.get(i).getY() == message.getEmetteur().getPositionActuelle().getY()) {
                System.out.println("emetteurSupprime: " + positions.get(i).getX() + ";" + positions.get(i).getY());
                positions.remove(i);
                break;
            }
        }
    }

    //Pour recuperer toutes les cases vides
    public ArrayList<Position> casesDisponible(ArrayList<Position> positions){
        ArrayList<Position> resultat = new ArrayList<Position>();
        for(int i = 0; i < positions.size(); i++){
            if(grilleActuelle.getGrille().get(positions.get(i).getX()).get(positions.get(i).getY()).isVide()){
                resultat.add(positions.get(i));
            }
        }
        System.out.println("listeCasesLibres: " + resultat);
        return resultat;
    }

    //Pour recuperer toutes les cases qui ne sont pas deja en attente d'une reponse suite a l'envoi d'un message
    public ArrayList<Position> casesPreteARecevoirMessage(ArrayList<Position> positions){
        ArrayList<Position> resultat = new ArrayList<Position>();
        for(int i = 0; i < positions.size(); i++){
            if(!grilleActuelle.getGrille().get(positions.get(i).getX()).get(positions.get(i).getY()).getAgent().isInTransaction()){
                resultat.add(positions.get(i));
            }
        }
        return resultat;
    }

    //Ce que doit faire l'agent a la reception d'un message
    public void run() {

        if (messagerie.getMessages().size() != 0) {
            switch (messagerie.getMessages().get(0)) {
                case MOVE:
                    ArrayList<Position> casesAdjacentes = grilleActuelle.positionsCasesAdjacentes(grilleActuelle.getGrille().get(positionActuelle.getX()).get(positionActuelle.getY()));
                    ArrayList<Position> casesAdjDispo;

                    supprimerEmetteur(casesAdjacentes, messagerie.getMessages().get(0));
                    casesAdjDispo = this.casesDisponible(casesAdjacentes);

                    Random random = new Random();

                    if(casesAdjDispo.size() > 0){
                        int rdm = random.nextInt(casesAdjDispo.size());
                        this.action(grilleActuelle.getGrille().get(casesAdjDispo.get(rdm).getX()).get(casesAdjDispo.get(rdm).getY()));
                        System.out.println("CaseChoisie: " + grilleActuelle.getGrille().get(casesAdjDispo.get(rdm).getX()).get(casesAdjDispo.get(rdm).getY()).getPosition());
                    }
                    else{
                        ArrayList<Position> casesAdjDispoMessage = this.casesPreteARecevoirMessage(casesAdjacentes);

                        if(casesAdjDispoMessage.size() > 0) {
                            int rdm = random.nextInt(casesAdjDispoMessage.size());
                            this.action(grilleActuelle.getGrille().get(casesAdjDispoMessage.get(rdm).getX()).get(casesAdjDispoMessage.get(rdm).getY()));
                        }
                    }

                    messagerie.getMessages().remove(0);
                    break;

                case MOVE_IMPOSSIBLE:
                    messagerie.getMessages().remove(0);
                    break;
            }
            messageRecu = false;
            inTransaction = false;
        }


    }



    /////////////////////////////////////////// GETTER AND SETTER ///////////////////////////////////////////

    public Position getPositionActuelle() {
        return positionActuelle;
    }

    public void setPositionActuelle(Position positionActuelle) {
        this.positionActuelle = positionActuelle;
    }

    public Position getPositionFinale() {
        return positionFinale;
    }

    public void setPositionFinale(Position positionFinale) {
        this.positionFinale = positionFinale;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Grille getGrilleActuelle() {
        return grilleActuelle;
    }

    public void setGrilleActuelle(Grille grilleActuelle) {
        this.grilleActuelle = grilleActuelle;
    }

    public Grille getGrilleFinale() {
        return grilleFinale;
    }

    public void setGrilleFinale(Grille grilleFinale) {
        this.grilleFinale = grilleFinale;
    }

    public Messagerie getMessagerie() {
        return messagerie;
    }

    public void setMessagerie(Messagerie messagerie) {
        this.messagerie = messagerie;
    }

    public void setMessageRecu(boolean messageRecu) {
        this.messageRecu = messageRecu;
    }

    public boolean isInTransaction() {
        return inTransaction;
    }

    public void setInTransaction(boolean inTransaction) {
        this.inTransaction = inTransaction;
    }

    public int getCompteurDemandeConsecutive() {
        return compteurDemandeConsecutive;
    }

    public void setCompteurDemandeConsecutive(int compteurDemandeConsecutive) {
        this.compteurDemandeConsecutive = compteurDemandeConsecutive;
    }

    public Agent getDestinataireDernierMessage() {
        return destinataireDernierMessage;
    }

    public void setDestinataireDernierMessage(Agent destinataireDernierMessage) {
        this.destinataireDernierMessage = destinataireDernierMessage;
    }
}
