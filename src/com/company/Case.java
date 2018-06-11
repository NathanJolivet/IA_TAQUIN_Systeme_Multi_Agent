package com.company;

public class Case {
    private Grille grille;
    private Position position;
    private Agent agent = null;
    private boolean isReservee = false;

    public Case(Grille grille, Position position){
        this.grille = grille;
        this.position = position;

    }

    public boolean isVide(){
        if(agent == null){
            return true;
        }
        return false;
    }

    public double distanceTo(Case caze){
        return Math.sqrt(Math.pow(position.getX() - caze.getPosition().getX(), 2) + Math.pow(position.getY() - caze.getPosition().getY(), 2));
    }

    /////////////////////////////////////////// GETTER AND SETTER ///////////////////////////////////////////

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isReservee() {
        return isReservee;
    }

    public void setReservee(boolean reservee) {
        isReservee = reservee;
    }

    /////////////////////////////////////////// AFFICHAGE ///////////////////////////////////////////

    public String toString(){
        String affCase;
        if(isVide()){
            affCase = " _ ";
        }
        else{
            if(agent.getIndex() < 10) {
                affCase = " " + String.valueOf(agent.getIndex()) + " ";
            }
            else{
                affCase = " " + String.valueOf(agent.getIndex());
            }

        }
        return affCase;
    }

}
