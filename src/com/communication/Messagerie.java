package com.communication;

import com.company.*;

import java.util.ArrayList;

public class Messagerie {

    private ArrayList<Message> messages = new ArrayList<Message>();

    public void sendMessage(Agent emetteur, Agent destinataire, Message message){
        System.out.println("message envoyé:");
        System.out.println("\temetteur: agent n°" + emetteur.getIndex() + " - position: [" + (emetteur.getPositionActuelle().getX()+1) + "; " + (emetteur.getPositionActuelle().getY()+1) + "]");
        System.out.println("\tdestinataire: agent n°"+ destinataire.getIndex() + " - position: ["  + (destinataire.getPositionActuelle().getX()+1) + "; " + (destinataire.getPositionActuelle().getY()+1) + "]");
        System.out.println("\tContenu: " + message.toString() + "\n");
        message.setEmetteur(emetteur);
        destinataire.getMessagerie().getMessages().add(message);
        destinataire.setMessageRecu(true);
        destinataire.setInTransaction(true);
        if(destinataire.getIndex() == emetteur.getDestinataireDernierMessage().getIndex()) {
            emetteur.setCompteurDemandeConsecutive(emetteur.getCompteurDemandeConsecutive() + 1);
        }
        else{
            emetteur.setCompteurDemandeConsecutive(0);
            emetteur.setDestinataireDernierMessage(destinataire);
        }
    }


    /////////////////////////////////////////// GETTER AND SETTER ///////////////////////////////////////////

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
